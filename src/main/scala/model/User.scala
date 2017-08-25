package model

import reactivemongo.bson.{BSONDocumentWriter, Macros}

case class User(name: String) {

}

object User {
  implicit def writer: BSONDocumentWriter[User] = Macros.writer[User]
}