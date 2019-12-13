val conf = new SparkConf().setMaster("local[*").setAppName("simple-app")
val sparkContext = new SparkContext(conf)


//Loading data with Spark Context returns an RDD
val rdd: RDD[String] = sparkContext.textFile("names.txt")


//Also you can create an RDD by parallizing an existing Data
val data: Array[Int] = Array(1, 2, 3, 4, 5, 6, 6, 7, 7)
val dataRdd: RDD[Int] = sparkContext.parallelize(data)


//Given a sparkSession Configuration, it's spark context can also be used for the operations above
val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("simple-app")
    .getOrCreate()

val sparkContextSpark :SparkContext = spark.sparkContext