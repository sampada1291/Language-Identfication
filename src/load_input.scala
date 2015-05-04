import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import java.io._

object Test {
  def load_files(file1: String, file2: String) = {
    //initialize map for storing counts of all 6-grams per language
    var counts = Map[String,Int]()
    //storing a string with the input converted to the proper format
    val input = new StringBuilder()
    
    //get the text from the file (it is in the format of line number,text) and store it in a list
    //this is to remove the line number and store the complete text in a string
    var ip = io.Source.fromFile(file1).getLines.toList
    var ip1 = new ListBuffer[String]()
    for (i <- ip)
      ip1 += i.mkString.split('\t')(1)
    ip1.addString(input," ")
    //store the counts of all 6-gram in the counts map per language so there will be a lisbuffer per language with the 6-gram frequencies
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
    //write the counts map to a file in the format of (6-gram,frequency) per line so that it can be used directly by the predictor
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
    //loads all the files in a given directory
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