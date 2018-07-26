package com.opticdev.parsers.python3.pythonbridge

import java.io.InputStream

import com.opticdev.parsers.python3.pythonbridge.PythonParserManager.{python3Location, script}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.sys.process._

class PythonParserInstance {
  if (python3Location.isEmpty) throw new Exception("Python3 not found on host machine")

  var writerStore: java.io.OutputStream = (b: Int) => null

  private var _status = "pending"
  def status = _status

  private var inputStream: InputStream = null

  val process = Process ( Seq(python3Location.get, "-u", "-c", script) )
  val io = new ProcessIO (
    writer,
    (in) => {
      inputStream = in
    },
    err => {
      scala.io.Source.fromInputStream(err).getLines.foreach(i=> {
        println(Console.RED + i)
        println(Console.RESET)
      })
    },
    true
  )

  private def writer(output: java.io.OutputStream) = {
    writerStore = output
    _status = "open"
  }

  def parseCode(code: String): String = {

    val codePlusControlString = code.replaceAll("\n", "\u0085") + "\n"

    writerStore.write(codePlusControlString.getBytes)
    writerStore.flush()

    inputStream.available()
    scala.io.Source.fromInputStream(inputStream)
      .takeWhile(i=> i != '\u0004')
      .mkString
  }

  private val runningProcess = process.run(io)


  def exit = {
    writerStore.close()
    inputStream.close()
  }

  val exitFuture = Future {
    runningProcess.exitValue() //Blocks until this process exits and returns the exit code.
    _status = "closed"
    Some(runningProcess.toString)// return the process result
  }

}
