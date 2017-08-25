package dao

import com.google.inject.ImplementedBy
import model.User

import scala.concurrent.Future

@ImplementedBy(classOf[UserDaoImpl])
trait UserDao extends MongoDao {
  def save(user: User): Future[User]
}
