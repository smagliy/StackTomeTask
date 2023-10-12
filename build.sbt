ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "StackTomeTask",
    libraryDependencies ++= Seq(
      "org.jsoup" % "jsoup" % "1.15.3",
      "org.json4s" %% "json4s-jackson" % "4.1.0-M2",
      "org.apache.commons" % "commons-csv" % "1.8",
      "org.slf4j" % "slf4j-api" % "1.7.5",
      "org.slf4j" % "slf4j-simple" % "1.7.5",
    )
  )
