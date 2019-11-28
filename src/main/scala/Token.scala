import scala.util.matching.Regex

case class Token(
                  value: String,
                  tokenType: TokenType.Val
                )

object TokenType extends Enumeration {

  case class Val(
                  regexRaw: String,
                  keyword: Option[String] = None,
                  chars: Seq[Char] = Seq.empty[Char]
                ) extends super.Val {
    def regex: Regex = regexRaw.r
  }

  val AttlistToken = Val("""(<!ATTLIST)""", Some("<!ATTLIST"))
  val ElementToken = Val("""(<!ELEMENT)""", Some("<!ELEMENT"))

  val PcdataToken = Val("""(\(#PCDATA\))""", Some("(#PCDATA)"))
  val EmptyToken = Val("""(EMPTY)""", Some("EMPTY"))
  val AnyToken = Val("""(ANY)""", Some("ANY"))

  val CdataToken = Val("""(CDATA)""", Some("CDATA"))
  val NmtokenToken = Val("""(NMTOKEN)""", Some("NMTOKEN"))
  val IdrefToken = Val("""(IDREF)""", Some("IDREF"))

  val RequiredToken = Val("""(#REQUIRED)""", Some("#REQUIRED"))
  val ImpliedToken = Val("""(#IMPLIED)""", Some("#IMPLIED"))
  val FixedToken = Val("""(#FIXED)""", Some("#FIXED"))

  val Word = Val("""^[A-Za-z0-9\$\~\%]*$""")
  val Name = Val("""^([A-Za-z\_\:])[A-Za-z0-9\-\_\:]*$""")
  val NonReservedWord = Val("") // Represents both Word and Name
  val NonReservedWordChar = Val("")

  val Quotes = Val("""^[\"]$""", chars = Seq('\"'))
  val Delimiters = Val("""^[,|]$""", chars = Seq(',', '|'))
  val Optional = Val("""^[\?\*\+]$""", chars = Seq('?', '*', '+'))
  val PointyBrackets = Val("""^[\>]$""", chars = Seq('>'))
  val CurlyBrackets = Val("""^[\(\)]$""", chars = Seq('(', ')'))

  val Unknown = Val(".")

  val EndOfFile = Val("$")

  val reservedWords = Seq(AttlistToken, ElementToken, PcdataToken, EmptyToken, AnyToken, CdataToken, NmtokenToken, IdrefToken, RequiredToken, ImpliedToken, FixedToken)
  val nonReservedWords = Seq(Word, Name)
  val specialWords = Seq(Quotes, Delimiters, Optional, PointyBrackets, CurlyBrackets)
  val specialChars = specialWords.flatMap(_.chars)
}
