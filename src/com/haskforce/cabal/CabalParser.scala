package com.haskforce.cabal

import com.haskforce.cabal.psi.CabalTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NotNull

import scala.annotation.tailrec

class CabalParser extends PsiParser {
  @NotNull
  override def parse(root: IElementType, builder: PsiBuilder): ASTNode = {
    val marker = builder.mark()
    while (!builder.eof()) {
      parse(builder)
    }
    marker.done(root)
    builder.getTreeBuilt
  }

  private def parse(builder: PsiBuilder): Unit = {
    builder.getTokenType match {
      case CabalTypes.KEY => parseKeyValuePair(builder, builder.mark())
      case CabalTypes.COMPONENT_TYPE => parseComponent(builder, builder.mark())
      case _ => builder.advanceLexer()
    }
  }

  @tailrec
  private def parseKeyValuePair(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.getTokenType match {
      case CabalTypes.KEY => builder.advanceLexer(); parseKeyValuePair(builder, marker)
      case CabalTypes.COLON => builder.advanceLexer(); parseKeyValuePair(builder, marker)
      case CabalTypes.VALUE_LINE =>
        parseValue(builder, builder.mark())
        marker.done(CabalTypes.KEY_VALUE_PAIR)
      case _ =>
        builder.remapCurrentToken(TokenType.BAD_CHARACTER)
        marker.done(CabalTypes.KEY_VALUE_PAIR)
    }
  }

  @tailrec
  private def parseValue(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.lookAhead(1) match {
      case CabalTypes.VALUE_LINE => builder.advanceLexer(); parseValue(builder, marker)
      case _ => builder.advanceLexer(); marker.done(CabalTypes.VALUE)
    }
  }

  @tailrec
  private def parseComponent(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.getTokenType match {
      case CabalTypes.COMPONENT_TYPE | CabalTypes.COMPONENT_NAME =>
        builder.advanceLexer(); parseComponent(builder, marker)
      case CabalTypes.KEY => parseKeyValuePair(builder, builder.mark()); parseComponent(builder, marker)
      case _ => marker.done(CabalTypes.COMPONENT)
    }
  }
}
