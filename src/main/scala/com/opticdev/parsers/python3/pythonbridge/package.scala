package com.opticdev.parsers.python3

import play.api.libs.json.JsObject

package object pythonbridge {
  sealed trait PythonBridgeProtocol
  case class ParsePython(code: String) extends PythonBridgeProtocol

  sealed trait ASTResponse extends PythonBridgeProtocol
  case class ASTError(error: String) extends PythonBridgeProtocol
  case class AST(ast: JsObject) extends PythonBridgeProtocol
}
