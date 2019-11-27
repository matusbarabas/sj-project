import scala.io.Source
import scala.collection.mutable.ListBuffer

object SyntaxAnalysis {

  def initializeAndLoadParseTable(): collection.mutable.Map[String, Map[String, String]] = {
    val bufferedSource = Source.fromFile("parsing_tableCSV.csv")
    val terminals = bufferedSource.getLines().next().drop(1).split("\t").toList

    val parsingTable = collection.mutable.Map.empty[String, Map[String, String]]

    for (line <- bufferedSource.getLines()) {
      val parsedLine = line.split("\t").toList
      val lineMap = (terminals zip parsedLine.drop(1)).toMap
      parsingTable.put(parsedLine.head, lineMap)
    }

    bufferedSource.close

    parsingTable
  }

  def analyzeSyntax(
    tokens: Seq[Token],
    parsingTable: collection.mutable.Map[String, Map[String, String]]
  ): Unit = {
    var pos = 0
    val nonReservedWordPos = 0

    var stack: ListBuffer[String] = ListBuffer()
    stack += "$"
    stack += "DTDOC'"
    stack += "DEC"

    var token = tokens(pos)

    while (stack.last != "$") {
      printStack(stack)
      token = tokens(pos)

      println("Token value: " + token.value + " type: " + token.tokenType)
      println("Top of stack value: " + stack.last)

      if (!parsingTable.keys.toList.contains(stack.last) | stack.last == "$") {
        if (token.value == stack.last) {
          println("POP - The top of the stack is the same as token.")
          stack = stack.dropRight(1)
          pos += 1
        } else {
          println("ERROR - No match in parsing table.")
        }
      } else {
        val rule = getRule(stack.last, token.value, parsingTable)
        if (rule != "error") {
          println("MATCH - Applying rules: " + stack.last + " -> " + rule)
          val rules = rule.split("\u00A0").toList
          stack = stack.dropRight(1)
          stack ++= rules.reverse
        } else {
          println("ERROR - No match in parsing table.")
        }
      }
    }

    println("DONE")
  }

  def getRule(
    nonTerminal: String,
    terminal: String,
    parsingTable: collection.mutable.Map[String, Map[String, String]]
  ): String = {
    parsingTable.get(nonTerminal).flatMap(_.get(terminal)) match {
      case Some(rule) => rule
      case _ => sys.error("Parsing table out of bounds.")
    }
  }

  def printRules(rules: List[String]): Unit = {
    rules.foreach(rule => print(rule + " "))
  }

  def printStack(stack: ListBuffer[String]): Unit = {
    println("----- ----- ----- ----- ----- STACK ----- ----- ----- ----- -----")
    stack.reverse.foreach(println(_))
    println("----- ----- ----- ----- ----- END STACK ----- ----- ----- ----- -")
  }
}
