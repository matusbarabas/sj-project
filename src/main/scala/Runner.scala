object Runner {
  def main(args: Array[String]): Unit = {
    val tokens = Tokenizer.tokenize(ExampleInputs.example1)
    println("----- ----- ----- ----- ----- TOKENS ----- ----- ----- ----- -----")
    tokens.foreach(t => println(s"${t.value}${" " * (12 - t.value.length)} ${t.tokenType}"))
  }
}