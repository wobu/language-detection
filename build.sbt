name := "language-detection"

organization := "com.cybozu.labs"

version := "0.99"

scalaVersion := "2.11.2"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % "test"

scalacOptions in Compile ++= Seq("-target:jvm-1.7", "-deprecation", "-unchecked", "-feature", "-language:postfixOps", "-language:implicitConversions")

javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7")