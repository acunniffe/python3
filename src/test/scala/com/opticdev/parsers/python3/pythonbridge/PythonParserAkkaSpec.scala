package com.opticdev.parsers.python3.pythonbridge

import akka.actor.{ActorSystem, Props}
import akka.dispatch.Futures
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSpec, FunSpecLike}
import akka.pattern._
import org.scalatest.concurrent.PatienceConfiguration.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class PythonParserAkkaSpec extends FunSpec with BeforeAndAfterAll {
  implicit val system = ActorSystem("PythonParserAkkaSpecSystem")

  implicit val timeout: akka.util.Timeout = akka.util.Timeout(1 minute)

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  it("individual actors can parse python") {
    val parser = system.actorOf(Props[PythonParserActor])
    val future = parser ? ParsePython("hello = world")
    val result = Await.result(future, 5 seconds)
    assert(result.isInstanceOf[AST])
  }

  it("routers can be used for for parallelism") {

    val router = system.actorOf(Props[PythonParserRouter])

    val futures = (0 to 10000).map(i=> router ? ParsePython(s"hello = $i"))

    assert(Await.result(futures(0), 10 seconds).asInstanceOf[AST].ast.toString().contains("0"))
    assert(Await.result(futures(1), 10 seconds).asInstanceOf[AST].ast.toString().contains("1"))
    assert(Await.result(futures(2), 10 seconds).asInstanceOf[AST].ast.toString().contains("2"))

  }

}
