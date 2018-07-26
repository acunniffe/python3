package com.opticdev.parsers.python3.pythonbridge

import org.scalatest.FunSpec
import play.api.libs.json.Json

class PythonParserInterfaceSpec extends FunSpec {

  it("works and closes when done") {
    val ppI = new PythonParserInstance
    val parsed = ppI.parseCode("hello = world")
    assert(Json.parse(parsed) == Json.parse("""{"range": null, "properties": {"body": [{"range": {"start": {"col": 0, "row": 1}, "end": {"col": 13, "row": 1}}, "properties": {"targets": [{"range": {"start": {"col": 0, "row": 1}, "end": {"col": 13, "row": 1}}, "properties": {"id": "hello", "ctx": "Store"}, "type": "Name"}], "value": {"range": {"start": {"col": 8, "row": 1}, "end": {"col": 13, "row": 1}}, "properties": {"id": "world", "ctx": "Load"}, "type": "Name"}}, "type": "Assign"}]}, "type": "Module"}"""))
    ppI.exit
    Thread.sleep(100)
    assert(ppI.status == "closed")
  }

  it("can handle multiple messages synchronously") {
    val ppI = new PythonParserInstance
    val parsed1 = ppI.parseCode("hello = world")
    assert(Json.parse(parsed1) == Json.parse("""{"range": null, "type": "Module", "properties": {"body": [{"range": {"end": {"row": 1, "col": 13}, "start": {"row": 1, "col": 0}}, "type": "Assign", "properties": {"value": {"range": {"end": {"row": 1, "col": 13}, "start": {"row": 1, "col": 8}}, "type": "Name", "properties": {"ctx": "Load", "id": "world"}}, "targets": [{"range": {"end": {"row": 1, "col": 13}, "start": {"row": 1, "col": 0}}, "type": "Name", "properties": {"ctx": "Store", "id": "hello"}}]}}]}}"""))
    val parsed2 = ppI.parseCode("you = me")
    assert(Json.parse(parsed2) == Json.parse("""{"range": null, "type": "Module", "properties": {"body": [{"range": {"end": {"row": 1, "col": 8}, "start": {"row": 1, "col": 0}}, "type": "Assign", "properties": {"value": {"range": {"end": {"row": 1, "col": 8}, "start": {"row": 1, "col": 6}}, "type": "Name", "properties": {"ctx": "Load", "id": "me"}}, "targets": [{"range": {"end": {"row": 1, "col": 8}, "start": {"row": 1, "col": 0}}, "type": "Name", "properties": {"ctx": "Store", "id": "you"}}]}}]}}"""))
    ppI.exit
  }

  it("works with line breaks / escape chars") {
    val ppI = new PythonParserInstance
    val parsed1 = ppI.parseCode("hello = world \n\nme = too")
    println(parsed1)
    ppI.exit
  }

  it("keeps buffers from leaking memory") {
    val ppI = new PythonParserInstance
    (0 to 100).map(i=> {
      val result = ppI.parseCode(s"hello = ${i}")
      assert(result.contains(s"""{"n": $i}"""))
    })

    ppI.exit
  }

}
