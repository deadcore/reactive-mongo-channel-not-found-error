package dao

import mongo.ReactiveMongoApi
import reactivemongo.api.DefaultDB
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

trait MongoDao {

  implicit val executionContext: ExecutionContext

  val reactiveMongoApi: ReactiveMongoApi

  val collectionName: String

  def db: Future[DefaultDB] = reactiveMongoApi.connection.database("eqs")

  def withCollection[T](op: BSONCollection => Future[T]): Future[T] = db.map(_.collection[BSONCollection](collectionName)).flatMap(op).recoverWith {
    case NonFatal(e) => Future.failed(DbOperationFailedException(s"DB operation failed: ${e.getMessage}", e))
  }

  case class DbOperationFailedException(error: String, ex: Throwable) extends RuntimeException(error, ex)

}