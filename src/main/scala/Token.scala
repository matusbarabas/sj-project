import scala.util.matching.Regex

case class Token (
  value: String,
  tokenType: TokenType.Val
)

object TokenType extends Enumeration {
  case class Val(
    regexRaw: String,
    chars: Seq[Char] = Seq.empty[Char]
  ) extends super.Val {
    def regex: Regex = regexRaw.r
  }

  val AttlistToken = Val("""(!ATTLIST)""")
  val ElementToken = Val("""(!ELEMENT)""")

  val PcdataToken = Val("""(\(#PCDATA\))""")
  val EmptyToken = Val("""(EMPTY)""")
  val AnyToken = Val("""(ANY)""")

  val CdataToken = Val("""(CDATA)""")
  val NmtokenToken = Val("""(NMTOKEN)""")
  val IdrefToken = Val("""(IDREF)""")

  val RequiredToken = Val("""(#REQUIRED)""")
  val ImpliedToken = Val("""(#IMPLIED)""")
  val FixedToken = Val("""(#FIXED)""")

  val Word = Val("""^[A-Za-z0-9\$\~\%]*$""")
  val Name = Val("""^([A-Za-z\_\:])[A-Za-z0-9\-\_\:]*$""")
  val NonReservedWord = Val("") // Represents both Word and Name

  val Quotes = Val("""^[\"]$""", Seq('\"'))
  val Delimiters = Val("""^[,|]$""", Seq(',','|'))
  val Optional = Val("""^[\?\*\+]$""", Seq('?','*','+'))
  val PointyBrackets = Val("""^[\<\>]$""", Seq('<','>'))
  val CurlyBrackets = Val("""^[\(\)]$""", Seq('(',')'))

  val Unknown = Val(".")

  val reservedWords = Seq(AttlistToken, ElementToken, PcdataToken, EmptyToken, AnyToken, CdataToken, NmtokenToken, IdrefToken, RequiredToken, ImpliedToken, FixedToken)
  val nonReservedWords = Seq(Word, Name)
  val specialWords = Seq(Quotes, Delimiters, Optional, PointyBrackets, CurlyBrackets)
  val specialChars = specialWords.flatMap(_.chars)
}
