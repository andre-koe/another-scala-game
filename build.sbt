ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / sbtVersion := "1.8.2"
ThisBuild / scalaVersion := "3.2.0"

lazy val root = (project in file("."))
  .settings(
      name := "another-scala-game",
      libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.16",
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
      libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31",
      libraryDependencies += "io.circe" %% "circe-core" % "0.14.5",
      libraryDependencies += "io.circe" %% "circe-generic" % "0.14.5",
      libraryDependencies += "io.circe" %% "circe-parser" % "0.14.5",
      libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
      libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
      libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
  )
