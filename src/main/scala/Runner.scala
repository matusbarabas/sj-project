object Runner {

  def main(args: Array[String]): Unit = {
    var tokens = Tokenizer.run(ExampleInputs.example4)
    tokens = tokens :+ Token("$", TokenType.EndOfFile)
    println("----- ----- ----- ----- ----- TOKENS ----- ----- ----- ----- -----")
    tokens.foreach(t =>
      println(s"${t.value}${" " * (12 - t.value.length)} ${t.tokenType}")
    )
    val parsingTable = SyntaxAnalysis.initializeAndLoadParseTable()
    SyntaxAnalysis.analyzeSyntax(tokens, parsingTable)
  }

}