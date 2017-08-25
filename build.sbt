name := "reactive-mongo-issue"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "eu.rekawek.toxiproxy" % "toxiproxy-java" % "2.1.1",
  "org.reactivemongo" % "reactivemongo_2.12" % "0.12.5",
  "com.google.inject" % "guice" % "4.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "net.codingwell" %% "scala-guice" % "4.1.0"
)