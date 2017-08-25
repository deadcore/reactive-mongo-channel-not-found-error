package dao

import javax.inject.Inject

import model.User
import mongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult

import scala.concurrent.{ExecutionContext, Future}

class UserDaoImpl @Inject()(val reactiveMongoApi: ReactiveMongoApi)(implicit val executionContext: ExecutionContext) extends UserDao {

  val collectionName: String = "xs"

  def save(x: User): Future[User] = {
    withCollection { coll =>
      coll.insert(x).flatMap {
        case wr: WriteResult if wr.ok => Future.successful(x)
      }
    }
  }

}