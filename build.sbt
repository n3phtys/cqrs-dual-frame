import sbt.Keys._

name := "cqrs-dual-frame"

scalaVersion in ThisBuild := "2.12.1"






lazy val root = project.in(file(".")).
  aggregate(cqrsdualframeJS, cqrsdualframeJVM).
  settings(
    publish := {},
    publishLocal := {}
  )



lazy val cqrsdualframe = crossProject.in(file(".")).
  settings(
    name := "cqrs-dual-frame",
    organization := "org.bitbucket.nephtys",
    version := "0.0.0",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "0.4.4",

    scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.8", "-unchecked",
      "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint")

  )//.configureJVM(_.dependsOn(genericProtocolProject))
  //.configureJS(_.dependsOn(genericProtocolProject))
  .
  jvmSettings(
  // Add JVM-specific settings here
  mainClass in run := Some("nephtys.loom.protocol.vanilla.solar.Main"),
  libraryDependencies += "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided"
).
  jsSettings(
    // Add JS-specific settings here
  )

lazy val cqrsdualframeJS = cqrsdualframe.js
lazy val cqrsdualframeJVM = cqrsdualframe.jvm
