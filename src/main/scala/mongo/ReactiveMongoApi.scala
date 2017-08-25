package mongo

import reactivemongo.api.{MongoConnection, MongoDriver}

trait ReactiveMongoApi {
  def driver: MongoDriver
  def connection: MongoConnection
}

