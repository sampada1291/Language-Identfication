import scala.io.Source
import scala.collection.mutable.ListBuffer
import java.io.File

/**
 * @author sampada
 */
class lang_id {
  val counts= scala.collection.mutable.HashMap[String, collection.mutable.ListBuffer[String]]()
  val probabilities=scala.collection.mutable.HashMap[String,Double]()
  
  def load_input() = {  
    val d = new File("/home/sampada/Downloads/NLP/")
    val files=d.listFiles.filter(_.isFile).toList
    for (file <- files)
    {
      val temp=file.toString.split("/").toList.last.toString
      val temp1 =temp.substring(0,temp.length-4).toString
      val lines = scala.io.Source.fromFile(file).getLines
      for (i <- lines)
      {
        counts.getOrElseUpdate(temp1, ListBuffer()) += "("+i.substring(0,i.length-2)+","+i.substring(i.length-1)+")"
      }
    }
  }
  
  def prediction(test: String) = {
    val input = new StringBuilder()
    
    var ip = io.Source.fromFile(test).getLines.toList
    var ip1 = new ListBuffer[String]()
    for (i <- ip)
      ip1 += i.mkString.split('\t')(1)
    ip1.addString(input," ")
    var k = 0
    var prob = 1.toDouble
    for (i <- 6 to test.length by 6)
    {
      var temp_map = 0
      val temp = test.substring(k,i)
      
      for ((key,value) <- counts)
      {
        var freq=0
        var flag=false
        for (entry <- counts(key))
        {
          if(entry.toString.substring(1,7) == temp)
          {
            freq=entry(entry.length-2)
            flag=true
          }
        }
        temp_map = counts(key).filter(entry => entry.toString.substring(1,6) == temp.substring(0,5)).size
        prob = prob*((1+freq).toDouble/(temp_map+freq).toDouble)
        probabilities(key)=prob
      }
      k += 6
    }
    
    var id=""
    var max=0
    
    for((key,value) <- probabilities)
      if(value>max)
        id=key
        
    print("The text is in $id")
  }
}


object Classes {
  def main(args: Array[String]) {
    val lid = new lang_id()
    lid.load_input()
    lid.prediction(file_name)
  }
}