import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import java.io._

object Test {
  def load_files(file1: String, file2: String) = {
    var counts = Map[String,Int]()
    val input = new StringBuilder()
    
    var ip = io.Source.fromFile(file1).getLines.toList
    var ip1 = new ListBuffer[String]()
    for (i <- ip)
      ip1 += i.mkString.split('\t')(1)
    ip1.addString(input," ")
    var k = 0
    ip1 = new ListBuffer[String]()
    for (i <- 6.toInt to input.length-1 by 6)
    {
      val temp = input.substring(k,i).toString
      ip1 += temp
      k += 6
    }
    counts = ip1.groupBy(w => w).mapValues(_.size)
    
    val counts_list = counts.toList
    
    val pw = new PrintWriter(new File(file2))
    print("writing to file")
    for (i <- counts_list)
    {
      val words = i.toString.stripPrefix("(").stripSuffix(")").trim
      pw.write(words)
      pw.write("\n")
    }
    pw.close
  }
  
  def main(args: Array[String]) {
    
    val d = new File("/home/sampada/Downloads/Input/")
    val files=d.listFiles.filter(_.isFile).toList
    for (file <- files)
    {
      val temp=file.toString.split("/").last.split("_")(0)+".txt"
      load_files(file.toString,temp)
    }
    //load_files("/home/sampada/Downloads/eng_wikipedia_2010_10K-text/eng_wikipedia_2010_10K-sentences.txt","/home/sampada/Downloads/NLP/english.txt")
    //load_files("/home/sampada/Downloads/fra_wikipedia_2010_10K-text/fra_wikipedia_2010_10K-sentences.txt","/home/sampada/Downloads/NLP/french.txt")
  }
}