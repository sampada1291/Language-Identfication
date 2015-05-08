import scala.io.Source
import scala.collection.mutable.ListBuffer
import java.io.File

/**
 * @author sampada
 */
class lang_id {
  //store the data from the files into counts hashmap 
  val counts= scala.collection.mutable.HashMap[String, collection.mutable.ListBuffer[String]]()
  //probabilities variable is a hashmap with the probabilities per language
  val probabilities=scala.collection.mutable.HashMap[String,Double]()
  var n=0
  
  def load_input() = {
    //read each file in the directory and store the 6-gram frequencies per language in the hashmap 
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
        n=i.substring(0,i.length-2).size
      }
    }
  }
  
  def prediction(test: String) = {
    val input = new StringBuilder()
    //read the test data and store in a string
    var ip = io.Source.fromFile(test).getLines.toList
    var ip1 = new ListBuffer[String]()
    for (i <- ip)
      ip1 += i.mkString.split('\t')(1)
    ip1.addString(input," ")
    //for each language find the probability per key
    for ((key,value) <- counts)
    {
      var k = 0
      var prob = 0.toDouble
      //for each character find it's probability based on the previous 5 characters and use Kneser Ney smoothing for unknown 6-grams
      for (i <- n to input.length by 1)
      {
        val temp = input.substring(k,i)
        var freq1=0
        var flag1=false
        var freq2=0
        var flag2=false
        for (entry <- counts(key))
        {
          if(entry.toString.substring(1,7) == temp && (entry.length-2)==n)
          {
            freq1=entry(entry.length-2)
            flag1=true
          }
          if(entry.toString.substring(1,6) == temp && (entry.length-2)==(n-1))
          {
            freq2=entry(entry.length-2)
            flag2=true
          }
        }
        
        prob = prob+math.log((1+freq1).toDouble/(freq2+counts(key).size).toDouble)
        k += 1
      }
      probabilities(key)=prob
    }
    
    var (id, max) = probabilities.head
    //find the maximum probability and output the corresponding key which specifies the language
    for((key,value) <- probabilities)
    {
      println(key,value)
      if(value>max)
      {
        id=key
        max=value
      }
    }
    print("The text is in %s.",id)
  }
}

object Classes {
  def main(args: Array[String]) {
    val lid = new lang_id()
    //load the frequencies from the text files and the call predictor on the test data
    lid.load_input()
    lid.prediction("/home/sampada/Downloads/test.txt")
  }
}