package com.opticdev.parsers.python3.pythonbridge

import akka.actor.{Actor, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

class PythonParserRouter extends Actor {

  val numberOfParsers = 6

  var router = {
    val routees = Vector.fill(numberOfParsers) {
      val r = context.actorOf(Props[PythonParserActor])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case w: ParsePython =>
      router.route(w, sender())
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[PythonParserActor])
      context watch r
      router = router.addRoutee(r)
  }
}