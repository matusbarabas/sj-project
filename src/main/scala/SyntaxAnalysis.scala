import scala.io.Source
import scala.collection.mutable.Stack

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
    tokensIn: Seq[Token],
    parsingTable: collection.mutable.Map[String, Map[String, String]]
  ): Unit = {
    val tokens = tokensIn.flatMap { token =>
      if (token.tokenType == TokenType.NonReservedWord) {
        token.value.map(tokenChar => Token(tokenChar.toString, TokenType.NonReservedWordChar))
      } else Seq(token)
    }

    var pos = 0

    val stack = Stack[String]()
    stack.push("$")
    stack.push("DTDOC")

    var token = tokens(pos)

    while (stack.top != "$") {
      printStack(stack)
      token = tokens(pos)

      println("Token value: " + token.value + " type: " + token.tokenType)
      println("Top of stack value: " + stack.top)

      if (!parsingTable.keys.toList.contains(stack.top) | stack.top == "$") {
        if (token.value == stack.top) {
          println("POP - The top of the stack is the same as token.")
          stack.pop()
          pos += 1
        } else {
          println("ERROR - No match in parsing table.")
          recovery(stack)
        }
      } else {
        val rule = getRule(stack.top, token.value, parsingTable)
        if (rule == "error") {
          println("ERROR - No match in parsing table.")
          recovery(stack)
        } else if (rule == "ε") {
          println("MATCH - Applying rules: " + stack.top + " -> " + rule)
          stack.pop()
          println("POP - Rule is epsilon.")
        } else {
          println("MATCH - Applying rules: " + stack.top + " -> " + rule)
          val rules = rule.split("\u00A0").toList
          stack.pop()
          rules.reverse.foreach(stack.push)
        }
      }
    }

    println("DONE")
  }

  def recovery(stack: Stack[String]): Unit = {
    if (stack.top == "|") stack.pop()
    else sys.error("ERROR - No match in parsing table, end of execution.")
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

  def printStack(stack: Stack[String]): Unit = {
    println("----- ----- ----- ----- ----- STACK ----- ----- ----- ----- -----")
    stack.foreach(println(_))
    println("----- ----- ----- ----- ----- END STACK ----- ----- ----- ----- -")
  }
}
