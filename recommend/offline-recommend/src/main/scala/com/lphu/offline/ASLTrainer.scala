package com.lphu.offline

import breeze.numerics.sqrt
import com.lphu.offline.OfflineRecommend.MONGODB_RATING_COLLECTION
import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ASLTrainer {

  def main(args: Array[String]): Unit = {
    val config = Map(
      "spark.cores" -> "local[*]",
      "mongo.uri" -> "mongodb://localhost:27017/recommender",
      "mongo.db" -> "recommender"
    )
    // 创建一个spark config
    val sparkConf = new SparkConf().setMaster(config("spark.cores")).setAppName("OfflineRecommender")
    // 创建spark session
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._
    implicit val mongoConfig = MongoConfig(config("mongo.uri"), config("mongo.db"))

    // 加载数据
    val ratingRDD = spark.read
      .option("uri", mongoConfig.uri)
      .option("collection", MONGODB_RATING_COLLECTION)
      .format("com.mongodb.spark.sql")
      .load()
      .as[ProductRating]
      .rdd
      .map(
        rating => Rating(rating.userId, rating.productId, rating.score)
      ).cache()


    val splits = ratingRDD.randomSplit(Array(0.8, 0.2))
    val trainRDD = splits(0)
    val testRDD = splits(1)

    // 输出最优参数
    val res = adjustALSParams(trainRDD, testRDD)
    spark.stop()

  }

  def adjustALSParams(trainData: RDD[Rating], testData: RDD[Rating]) : Unit = {
    val result = for (rank <- Array(5, 10, 25, 50); lambda <- Array(1, 0.1, 0.01))
      yield {
        val model = ALS.train(trainData, rank, 10, lambda)
        val rmse = getRMSE(model, testData)
        (rank, lambda, rmse)
      }
    println(result.minBy(_._3))
  }

  def getRMSE(model: MatrixFactorizationModel, data: RDD[Rating]) : Double = {

    val userProducts = data.map(item => (item.user, item.product))
    val predictRating = model.predict(userProducts)

    val observed = data.map(item => ((item.user, item.product),  item.rating))
    val predict = predictRating.map(item => ((item.user, item.product), item.rating))

    sqrt(
      observed.join(predict).map{
        case ((userId, productId), (actual, pre)) =>
          val err = actual - pre
          err * err
      }.mean()
    )
  }


}
