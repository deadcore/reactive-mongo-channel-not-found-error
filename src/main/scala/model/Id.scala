package model

import java.time.{Instant, LocalDateTime, ZoneId}

import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID, Macros}

case class Id($oid: String) {
  def created = LocalDateTime.ofInstant(Instant.ofEpochMilli(BSONObjectID.parse($oid).map(id => id.time).getOrElse(Instant.now().toEpochMilli)), ZoneId.of("UTC"))
}

case object Id {
  def generate: Id = Id($oid = BSONObjectID.generate().stringify)

  implicit def user: BSONDocumentWriter[Id] = Macros.writer[Id]
}
