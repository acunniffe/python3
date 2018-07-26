name := "python3"

version := "0.1"

organization := "com.opticdev.parsers"

scalaVersion := "2.12.4"

libraryDependencies += "com.opticdev" %% "parser-foundation" % "0.1.4"

libraryDependencies += "com.opticdev" %% "marvin-runtime" % "0.1.4"
libraryDependencies += "com.opticdev" %% "marvin-common" % "0.1.4"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.3"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.4"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.5.4"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.4"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"