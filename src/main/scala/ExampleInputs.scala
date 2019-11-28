object ExampleInputs {
  val example1: String =
    """
      |<!ELEMENT note (to,from,heading,body)>
      |<!ELEMENT to (#PCDATA)>
      |<!ELEMENT from (#PCDATA)>
      |<!ELEMENT heading (#PCDATA)>
      |<!ELEMENT body (#PCDATA)>
    """.stripMargin

  val example2: String = "<!ELEMENT to (#PCDATA)>"

  val example3: String = "<!ATTLST akj | fiit IDREF #REQUIRED>"
}
