package com.opticdev.parsers.python3.pythonbridge

import org.scalatest.FunSpec

class PythonParserSpec extends FunSpec {

  it("can find local python3 if installed") {
    val installedPath = PythonParserManager.python3Location
    assert(installedPath.isDefined)
    assert(installedPath.get.contains("python3"))
  }

  it("can get the script") {
    assert(PythonParserManager.script.startsWith("from __future"))
  }

}
