import scala.io.Source
import scala.collection.mutable.ListBuffer
import java.io.File

/**
 * @author sampada
 */
class lang_id {
  val counts= scala.collection.mutable.Map[String,Int]()
  
  def load_input() = {  
    val d = new File("/home/sampada/Downloads/NLP/")
    val files=d.listFiles.filter(_.isFile).toList
    for (file <- files)
    {
      val lines = scala.io.Source.fromFile(file).getLines
      for (i <- lines)
      {
        counts += i.substring(0,i.length-2) -> i.substring(i.length-1).toInt
      }
    }
  }
  /**
  def prediction() = {
    var ip = io.Source.fromFile("/home/sampada/Downloads/fra_wikipedia_2010_10K-text/fra_wikipedia_2010_10K-sentences.txt").getLines.toList
    var ip1 = new ListBuffer[String]()
    for (i <- ip)
      ip1 += i.mkString.split('\t')(1)
    var predict = new StringBuilder()
    ip1.addString(predict," ")
    var k = 0
    var prob_eng = 1.toDouble
    for (i <- 6 to 304 by 6)
    {
      var temp_map = 0
      val temp = predict.substring(k,i)
      if(counts_eng.contains(temp)) {
        temp_map = counts_eng.filter((namePhone: (String, Int)) => namePhone._1.substring(0,5) == temp.substring(0,5)).size
        prob_eng = prob_eng*((1+counts_eng(temp)).toDouble/(temp_map+counts_eng.size).toDouble)}
      
      k += 6
    }
  }*/
}


object Classes {
  def main(args: Array[String]) {
    val lid = new lang_id()
    lid.load_input()
    //lid.prediction()
  }
}