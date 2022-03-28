import Dependencies._
import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "lets-learn-zio",
    libraryDependencies += "dev.zio" %% "zio-streams" % "1.0.13",
    libraryDependencies += "dev.zio" %% "zio" % "1.0.13"
  )

