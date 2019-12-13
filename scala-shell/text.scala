import java.io._
import java.util.Properties
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

/*
 * Writes the input array to the specified textfile.
 */
def writeFile(filename : String, lines : Array[String]) : Unit = {
	val file = new File(filename)
	val bw = new BufferedWriter(new FileWriter(file))
	for(line <- lines) {
		bw.write(line)
	}
	bw.close()
}

/*
 * Writes the input array to the specified textfile.
 */
def writeFile(filename : String, lines : Array[(String, Int)]) : Unit = {
	val file = new File(filename)
	val bw = new BufferedWriter(new FileWriter(file))
	for(line <- lines) {
		bw.write(line._1 + ": " + line._2.toString + "\n")
	}
	bw.close()
}

/*
 * Reverses the lines in inputFileName and saves them to outputFileName
 */
def reverseNames(inputFileName : String, outputFileName : String) : Unit = {
	val text = sc.textFile(inputFileName) 
	val rev = text.flatMap(line => line.split("\n")).map(word => word.reverse + "\n")
	writeFile(outputFileName, rev.collect)
}

/*
 * Counts the occurance of each word in the inputFile and writes the result
 * to the specified output file.
 */
def wordCount(inputFileName : String, outputFileName : String) : Unit = {
	val text = sc.textFile(inputFileName) 
	val counts = text.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_+_)
	writeFile(outputFileName, counts.collect)
}

wordCount("skrip_input.txt", "count.txt")
reverseNames("names.txt", "reverse.txt")
