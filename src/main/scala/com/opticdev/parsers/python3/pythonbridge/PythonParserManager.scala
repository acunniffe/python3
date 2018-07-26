package com.opticdev.parsers.python3.pythonbridge

import akka.actor.{ActorSystem, Props}

import scala.sys.process._
import scala.util.Try

object PythonParserManager {

  val pythonActorSystem = ActorSystem("python-actor-system")

  lazy val script: String = {
    val path = this.getClass.getClassLoader.getResource("astrepl.py")
    scala.io.Source.fromInputStream(path.openStream()).mkString
  }

  lazy val python3Location: Option[String] = Try {
    val result = Seq("bash", "--login", "-c", "which python3").!!
    result.replace("\n", "")
  }.toOption



  lazy val parserRouter = pythonActorSystem.actorOf(Props[PythonParserRouter])

}
