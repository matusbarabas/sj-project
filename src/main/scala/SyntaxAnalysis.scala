import scala.io.Source
import scala.collection.mutable.Stack

object SyntaxAnalysis {

  var recoveryFlag = false
  var stepsFlag = false

  def stepPrintln: String => Unit = Utility.stepPrintln(stepsFlag)

  def success(): Unit = println(Console.GREEN + "\nResult: The input is a valid basicDTD string." + Console.RESET)
  def failure(): Unit = println(Console.RED + "\nResult: The input is not a valid basicDTD string." + Console.RESET)

  def initializeAndLoadParseTable(): collection.mutable.Map[String, Map[String, String]] = {
    val bufferedSource = Source.fromFile("parsing_tableCSV.csv")("UTF-8")
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
    println("##################################################################")
    println("#                                                                #")
    println("#                        SYNTAX ANALYSIS                         #")
    println("#                                                                #")
    println("##################################################################")
    println()

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
          stepPrintln(Console.GREEN + "POP - The top of the stack is the same as token." + Console.RESET)
          stack.pop()
          pos += 1
        } else {
          stepPrintln(Console.GREEN + "ERROR - No match in parsing table." + Console.RESET)
          recovery(stack)
        }
      } else {
        val rule = getRule(stack.top, token.value, parsingTable)
        if (rule == "error") {
          stepPrintln(Console.GREEN + "ERROR - No match in parsing table.")
          recovery(stack)
        } else if (rule == "epsilon") {
          println(Console.GREEN + "MATCH - Applying rules: " + stack.top + " -> " + rule + Console.RESET)
          stack.pop()
          stepPrintln("POP - Rule is epsilon.")
        } else {
          stepPrintln(Console.GREEN + "MATCH - Applying rules: " + stack.top + " -> " + rule + Console.RESET)
          val rules = rule.split(" ").toList
          stack.pop()
          rules.reverse.foreach(stack.push)
        }
      }
      println()
    }

    println(Console.GREEN + "DONE" + Console.RESET)
    success()
  }

  def recovery(stack: Stack[String]): Unit = {
    def exitOnError(): Unit = {
      println(Console.RED + "ERROR - No match in parsing table, end of execution." + Console.RESET)
      failure()
      System.exit(2)
    }

    if (recoveryFlag) {
      println("RECOVERY")
      if (stack.top == "ELEMCHILD''") {
        stack.pop()
        stack.push("ELEMCHILD'''")
      } else if (stack.top == "ELEMCHILD'") {
        stack.pop()
      } else {
        exitOnError()
      }
    } else {
      exitOnError()
    }


  }

  def getRule(
    nonTerminal: String,
    terminal: String,
    parsingTable: collection.mutable.Map[String, Map[String, String]]
  ): String = {
    parsingTable.get(nonTerminal).flatMap(_.get(terminal)) match {
      case Some(rule) => rule
      case _ =>
        //sys.error("Parsing table out of bounds.")
        println(Console.RED + "Parsing table out of bounds." + Console.RESET)
        failure()
        System.exit(2)
        ""
    }
  }

  def printRules(rules: List[String]): Unit = {
    rules.foreach(rule => print(rule + " "))
  }

  def printStack(stack: Stack[String]): Unit = {
    val maxLen = stack.map(_.length).max
    val dashes = ("----- " * math.ceil(maxLen / 10.0f).toInt).trim()
    val width = dashes.length + " STACK ".length + dashes.length
    val offset = (word: String, end: Int) => " " * (((width - 2 - word.length) / 2) + end * ((width - word.length) % 2))

    println(s"$dashes STACK $dashes")
    stack.foreach(word => println(s"|${offset(word, 0)}$word${offset(word, 1)}|"))
    println(s"$dashes ----- $dashes")
  }
}
