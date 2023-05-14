import Dependencies._
import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"
lazy val root = (project in file("."))
  .settings(
    name := "lets-learn-zio",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-streams" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.3.0-RC8",
      "com.azure" % "azure-storage-queue" % "12.6.0"
    )
  )

enablePlugins(JavaAppPackaging)
