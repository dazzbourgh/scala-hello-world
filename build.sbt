name := """play-scala-seed"""
organization := "zhi.yest"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.6"
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "zhi.yest.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "zhi.yest.binders._"
