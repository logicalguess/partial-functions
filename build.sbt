name := "partial-functions"

version := "0.1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "com.typesafe.akka" %% "akka-stream" % "2.4.17",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.17",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.17",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"

)