releaseSettings

name := "language-detection"

organization := "com.cybozu.labs"

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-target:jvm-1.7")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % "test"

libraryDependencies += "net.arnx" % "jsonic" % "1.3.0"


publishMavenStyle := true

crossPaths := false
