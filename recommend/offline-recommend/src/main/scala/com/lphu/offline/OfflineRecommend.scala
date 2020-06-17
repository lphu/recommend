package com.lphu.offline


import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SparkSession
import org.jblas.DoubleMatrix


case class ProductRating(userId: Int, productId: Int, score: Double, timestamp: BigInt)
case class MongoConfig(uri: String, db: String)

// 推荐对象
case class Recommendation(productId : Int, score : Double)

case class UserRecs(userId : Int, recs : Seq[Recommendation])
case class ProductRecs(productId : Int, recs : Seq[Recommendation])

object OfflineRecommend {

  val MONGODB_RATING_COLLECTION = "rating"

  val USER_RECS = "user_recs"
  val PRODUCT_RECS = "product_recs"
  val USER_MAX_RECOMMENDATION = 20


  def main(args: Array[String]): Unit = {
    val config = Map(
      "spark.cores" -> "local[*]",
      "mongo.uri" -> "mongodb://localhost:27017/recommender",
      "mongo.db" -> "recommender"
    )

    val sparkConf = new SparkConf().setMaster(config("spark.cores")).setAppName("OfflineRecommend")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._
    implicit val mongoConfig = MongoConfig(config("mongo.uri"), config("mongo.db"))

    val ratingRDD = spark.read
      .option("uri", mongoConfig.uri)
      .option("collection", MONGODB_RATING_COLLECTION)
      .format("com.mongodb.spark.sql")
      .load()
      .as[ProductRating]
      .rdd
      .map(
        rating => (rating.userId, rating.productId, rating.score)
      ).cache()


    val userRDD = ratingRDD.map(_._1).distinct()
    val productRDD = ratingRDD.map(_._2).distinct()

    // 核心计算过程
    val trainData = ratingRDD.map(x=>Rating(x._1, x._2, x._3))
    val model = ALS.train(trainData, rank = 5, iterations = 10, lambda = 0.1)

    val userProducts = userRDD.cartesian(productRDD)
    val preRating = model.predict(userProducts)


    // userRecs
    val userRecs = preRating.filter(_.rating > 0)
      .map(
        rating => (rating.user, (rating.product, rating.rating))
      )
      .groupByKey()
      .map {
        case (userId, userRecs) =>
          UserRecs(userId, userRecs.toList.sortWith(_._2>_._2).take(USER_MAX_RECOMMENDATION).map(x=>Recommendation(x._1, x._2)))
      }
      .toDF()

    userRecs.write
      .option("uri", mongoConfig.uri)
      .option("collection", USER_RECS)
      .mode("overwrite")
      .format("com.mongodb.spark.sql")
      .save()

    // productRecs
    val  productFeatures = model.productFeatures.map {
      case (productId, features) => (productId, new DoubleMatrix(features))
    }

    // 商品相似度
    val productRecs = productFeatures.cartesian(productFeatures)
      .filter {
        case(product1, product2) => product1._1 != product2._1
      }
      .map {  // 计算相似度
        case(product1, product2) =>
          val score = consinSim(product1._2, product2._2)
          (product1._1, (product2._1, score))
      }
      .groupByKey()
      .map {
        case(productId, productRecs) =>
          ProductRecs(productId, productRecs.toList.sortWith(_._2>_._2).map(product=>Recommendation(product._1, product._2)) )
      }
      .toDF()

    productRecs.write
      .option("uri", mongoConfig.uri)
      .option("collection", PRODUCT_RECS)
      .mode("overwrite")
      .format("com.mongodb.spark.sql")
      .save()

    spark.stop()

  }


  def consinSim(product1: DoubleMatrix, product2: DoubleMatrix): Double = {
    product1.dot(product2) / (product1.norm2() * product2.norm2())
  }
}
