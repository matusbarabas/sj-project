object Utility {

  def stepPrintln(flag: Boolean)(str: String): Unit = {
    if (flag) {
      print(str)
      scala.io.StdIn.readLine()
    } else {
      println(str)
    }
  }

}
