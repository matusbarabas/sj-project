import java.io.FileNotFoundException

import scala.io.BufferedSource

object Runner {

  var path = "input.txt"

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("""For program usage run this program with "-h" flag""")
      println("Using default input file path: ./input.txt\n")
    } else {
      val _args = args.flatMap { arg =>
        if(arg.startsWith("-")) {
          arg.map(flag => s"-${flag.toString}").drop(1)
        } else {
          Seq(arg)
        }
      }

      _args.foreach { flag =>
        if (flag == "-h") {
          println(
            """
              |basicDTD, usage:
              |  -h            shows this help
              |  -p [path]     path to the input file, if unspecified, takes in input.txt
              |  -l            enables automatic fixing of typos of reserved keyword, based on Levenshtein distance
              |  -s            enables recovery from incorrect usage of round brackets
              |  -L            enables step by step execution of lexical analyzer
              |  -S            enables step by step execution of syntax analyzer
            """.stripMargin)
          System.exit(0)
        } else if (flag == "-l") {
          Tokenizer.levenshteinFlag = true
        } else if (flag == "-s") {
          SyntaxAnalysis.recoveryFlag = true
        } else if (flag == "-L") {
          Tokenizer.stepsFlag = true
        } else if (flag == "-S") {
          SyntaxAnalysis.stepsFlag = true
        }
      }

      if (_args.contains("-p")) {
        val pathArgIndex = _args.indexOf("-p") + 1
        if (pathArgIndex < _args.length) {
          path = _args(pathArgIndex)
          println(s"Using input file path: $path\n")
        } else {
          println("Invalid path")
          System.exit(0)
        }
      } else {
        println("Using default input file path: ./input.txt\n")
      }
    }

    val source = (try {
      scala.io.Source.fromFile(path)("UTF-8")
    } catch {
      case _: FileNotFoundException =>
        println(s"The system cannot find the file specified: $path")
        System.exit(1)
    }).asInstanceOf[BufferedSource]
    val inputString = source.mkString
    source.close()
    var tokens = Tokenizer.run(inputString)
    tokens = tokens :+ Token("$", TokenType.EndOfFile)
    val parsingTable = SyntaxAnalysis.initializeAndLoadParseTable()
    SyntaxAnalysis.analyzeSyntax(tokens, parsingTable)
  }

}