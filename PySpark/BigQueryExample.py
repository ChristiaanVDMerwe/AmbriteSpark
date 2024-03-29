#!/usr/bin/python
"""https://cloud.google.com/dataproc/docs/tutorials/bigquery-connector-spark-example"""
"""BigQuery I/O PySpark example."""

"""
gcloud dataproc jobs submit pyspark BigQueryExample.py \
    --cluster=cluster-8035 	  \
    --jars=gs://spark-lib/bigquery/spark-bigquery-latest.jar
"""

from pyspark.sql import SparkSession
spark = SparkSession \
    .builder \
    .master('yarn') \
    .appName('spark-bigquery-demo') \
    .getOrCreate()

# Use the Cloud Storage bucket for temporary BigQuery export data used
# by the connector. This assumes the Cloud Storage connector for
# Hadoop is configured.
bucket = spark.sparkContext._jsc.hadoopConfiguration().get(
    'fs.gs.system.bucket')
spark.conf.set('temporaryGcsBucket', bucket)

# Load data from BigQuery.
words = spark.read.format('bigquery') \
    .option('table', 'bigquery-public-data:samples.shakespeare') \
    .load()
words.createOrReplaceTempView('words')

# Perform word count.
word_count = spark.sql(
    'SELECT word, SUM(word_count) AS word_count FROM words GROUP BY word')
word_count.show()
word_count.printSchema()

# Saving the data to BigQuery
word_count.write.format('bigquery') \
    .option('table', 'wordcount_dataset.wordcount_output') \
    .save()
