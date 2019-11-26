object Tokenizer {

  def checkGeneric(word: String, tokenTypes: Seq[TokenType.Val], upperType: Option[TokenType.Val] = None): Seq[Token] = {
    tokenTypes
      .map(tokenType => (tokenType, tokenType.regex.findFirstIn(word)))
      .flatMap {
        case (tokenType, Some(potentialMatch)) =>
          if (potentialMatch == word) {
            Seq(Token(word, if (upperType.isDefined) upperType.get else tokenType))
          } else {
            Seq.empty[Token]
          }
        case _ => Seq.empty[Token]
      }.distinct
  }

  def checkReserved(depth: Int)(word: String): Seq[Token] = {
    println(s"${" "*depth*4}Checking reserved")
    checkGeneric(word, TokenType.reservedWords)
  }

  def checkNonReserved(depth: Int)(word: String): Seq[Token] = {
    println(s"${" "*depth*4}Checking nonreserved")
    checkGeneric(word, TokenType.nonReservedWords, Option(TokenType.NonReservedWord))
  }

  def checkSpecial(depth: Int)(word: String): Seq[Token] = {
    println(s"${" "*depth*4}Checking special")
    val dbg = checkGeneric(word, TokenType.specialWords)
    dbg
  }

  def checkComplex(depth: Int)(word: String): Seq[Token] = {
    TokenType.reservedWords
      .map(reserved => word.replaceAll(reserved.regexRaw, "_$0_").split("_").filter(_.nonEmpty))
      .map(_.toSeq)
      .find(_.length > 1)
    match {
      case Some(words) => words.flatMap(tokenize(_, depth + 1))
      case _ =>
        val mutableWords = scala.collection.mutable.ListBuffer.empty[String]
        val wordBuilder = new StringBuilder()

        word.foreach { char =>
          if (TokenType.specialChars.contains(char)) {
            mutableWords += wordBuilder.result()
            mutableWords += char.toString
            wordBuilder.clear()
          } else {
            wordBuilder += char
          }
        }
        mutableWords += wordBuilder.result()

        val words = collection.immutable.Seq[String](mutableWords.filter(_.trim().nonEmpty): _*)
        if (words.size == 1) {
          Seq(Token(word, TokenType.Unknown))
        } else {
          words.flatMap(tokenize(_, depth + 1))
        }
    }
  }

  def chainExecution(word: String, depth: Int)(functions: Seq[String => Seq[Token]]): Seq[Token] = {
    functions match {
      case exec::tail =>
        val result = exec(word)
        if (result.nonEmpty) {
          result
        } else {
          chainExecution(word, depth)(tail)
        }
      case _ =>
        Seq(Token(word, TokenType.Unknown))
    }
  }

  def tokenize(string: String, depth: Int = 0): Seq[Token] = {
    val words = string.replaceAll("\\s", " ").split(" +").toSeq.filter(_.trim().nonEmpty)

    words.flatMap { word =>
      println(s"${" "*depth*4}Tokenizing word: $word")
      val tokens = chainExecution(word, depth)(Seq(checkReserved(depth), checkNonReserved(depth), checkSpecial(depth), checkComplex(depth)))
      tokens.foreach(token => println(s"""${" "*depth*4}Created token "${token.value}" of type "${token.tokenType}""""))
      println()
      tokens
    }
  }

}
