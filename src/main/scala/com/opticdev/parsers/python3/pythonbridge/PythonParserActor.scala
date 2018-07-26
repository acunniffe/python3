package com.opticdev.parsers.python3.pythonbridge

import akka.actor.{Actor, Terminated}
import play.api.libs.json.{JsObject, Json}

import scala.util.Try

class PythonParserActor extends Actor {

  val ppI = new PythonParserInstance

  override def receive: Receive = {
    case ParsePython(code) => {
      val raw = ppI.parseCode(code)

      val result = Try(raw).map(Json.parse).map(_.as[JsObject])

      if (result.isSuccess) {
        sender() ! AST(result.get)
      } else {
        sender() ! ASTError("Error parsing Python code: "+ raw)
      }
    }
    case Terminated => ppI.exit
  }
}
