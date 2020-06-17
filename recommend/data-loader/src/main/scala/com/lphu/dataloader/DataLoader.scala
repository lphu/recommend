package com.lphu.dataloader

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 商品类
 */
case class Product (productId : Int, name : String, imgUrl : String, categories : String, tags : String)

/**
 * 评分类
 */
case class Rating (userId: Int, productId: Int, score: Double, timestamp: Int)

/**
 * 连接池
 */
case class MongoConfig( uri: String, db: String )

object DataLoad {

  val PRODUCT_DATA_FILE = "../resources/products.csv"
  val RATING_DATA_FILE = "../resources/ratings.csv"

  val MONGODB_PRODUCT_COLLECTION = "product"
  val MONGODB_RATING_COLLECTION = "rating"

  def main(args: Array[String]): Unit = {
    val config = Map(
      "spark.cores" -> "local[*]",
      "mongo.uri" -> "mongodb://localhost:27017/recommender",
      "mongo.db" -> "recommender"
    )

    // spark config
    val sparkConf = new SparkConf().setMaster(config("spark.cores")).setAppName("DataLoader")
    // spark session
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._

    val productRDD = spark.sparkContext.textFile(PRODUCT_DATA_FILE)
    val productDF= productRDD.map(item => {
      val attr = item.split("\\^")
      Product(attr(0).toInt, attr(1).trim, attr(4).trim, attr(5).trim, attr(6).trim)
    }).toDF()

    val ratingRDD = spark.sparkContext.textFile(RATING_DATA_FILE)
    val ratingDF = ratingRDD.map( item => {
      val attr = item.split(",")
      Rating(attr(0).toInt, attr(1).toInt, attr(2).toDouble, attr(3).toInt)
    } ).toDF()

    implicit val mongoConfig = MongoConfig( config("mongo.uri"), config("mongo.db") )
    storeDataInMongoDB( productDF, ratingDF )
    spark.stop()
  }

  def storeDataInMongoDB(productDF: DataFrame, ratingDF: DataFrame)(implicit mongoConfig: MongoConfig) : Unit ={
    // mongo连接
    val mongoClient = MongoClient(MongoClientURI(mongoConfig.uri))

    val productCollection = mongoClient(mongoConfig.db)(MONGODB_PRODUCT_COLLECTION)
    val ratingCollection = mongoClient(mongoConfig.db)(MONGODB_RATING_COLLECTION)



    productDF.write
      .option("uri", mongoConfig.uri)
      .option("collection", MONGODB_PRODUCT_COLLECTION)
      .mode("overwrite")
      .format("com.mongodb.spark.sql")
      .save()

    ratingDF.write
      .option("uri", mongoConfig.uri)
      .option("collection", MONGODB_RATING_COLLECTION)
      .mode("overwrite")
      .format("com.mongodb.spark.sql")
      .save()

    productCollection.createIndex(MongoDBObject("productId" -> 1))
    ratingCollection.createIndex(MongoDBObject("productId" -> 1))
    ratingCollection.createIndex(MongoDBObject("userId" -> 1))
  }
}
