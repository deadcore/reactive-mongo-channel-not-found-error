package mongo

import reactivemongo.api.{MongoConnection, MongoDriver}

import scala.util.Try

class ReactiveMongoApiImpl(mongoUri: String) extends ReactiveMongoApi {

  val driver = MongoDriver()
  val parsedUri: Try[MongoConnection.ParsedURI] = MongoConnection.parseURI(mongoUri)
  val connection: MongoConnection = parsedUri.map(driver.connection).get

}