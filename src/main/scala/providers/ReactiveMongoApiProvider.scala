package providers


import com.google.inject.Provider
import mongo.{ReactiveMongoApi, ReactiveMongoApiImpl}

class ReactiveMongoApiProvider extends Provider[ReactiveMongoApi] {

  val driver1 = new reactivemongo.api.MongoDriver

  override def get(): ReactiveMongoApi = new ReactiveMongoApiImpl("mongodb://eqs:secret@localhost:27017,localhost:27018,localhost:27019/admin?authMode=scram-sha1&connectTimeoutMS=5000")



}