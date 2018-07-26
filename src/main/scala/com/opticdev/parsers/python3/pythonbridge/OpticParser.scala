package com.opticdev.parsers.python3.pythonbridge

import com.opticdev.parsers
import com.opticdev.parsers.graph.AstType
import com.opticdev.parsers.sourcegear.advanced
import com.opticdev.parsers.sourcegear.basic.BasicSourceInterface
import com.opticdev.parsers._

class OpticParser extends ParserBase {
  override def languageName: String = "python"

  override def parserVersion: String = "0.1.0"

  override def fileExtensions: Set[String] = Set("py")

  override def programNodeType: graph.AstType = AstType("Module", languageName)

  override def blockNodeTypes: parsers.BlockNodeTypes = BlockNodeTypes(
    BlockNodeDesc( AstType("Module", languageName), "body"),
    BlockNodeDesc( AstType("FunctionDef", languageName), "body"),
    BlockNodeDesc( AstType("ClassDef", languageName), "body")
  )

  override def identifierNodeDesc: IdentifierNodeDesc = IdentifierNodeDesc(AstType("Name", languageName), Seq("id"))

  override def basicSourceInterface: BasicSourceInterface = ???

  override def marvinSourceInterface: advanced.MarvinSourceInterface = ???

  override def parseString(contents: String): ParserResult = {

  }
}
