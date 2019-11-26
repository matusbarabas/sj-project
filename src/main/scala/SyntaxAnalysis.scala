import scala.io.Source

object SyntaxAnalysis {

  def initializeParseTable(): Unit = {
    val bufferedSource = Source.fromFile("parsing_tableCSV.csv")
    val terminals = bufferedSource.getLines().next().drop(1).split("\t").toList

    val parsingTable = scala.collection.mutable.Map.empty[String, Map[String, String]]

    for (line <- bufferedSource.getLines()) {
      val parsedLine = line.split("\t").toList
      val lineMap = (terminals zip parsedLine.drop(1)).toMap
      parsingTable.put(parsedLine.head, lineMap)
    }

    bufferedSource.close
  }

  // TODO ZMAZAT
  def main(args: Array[String]): Unit = {
    initializeParseTable()
  }
}
