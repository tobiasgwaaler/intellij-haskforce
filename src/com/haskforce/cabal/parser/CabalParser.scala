package com.haskforce.cabal.parser

import com.haskforce.cabal.psi.CabalTypes._
import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
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
      case KEY => keyValuePair(builder, builder.mark())
      case COMPONENT_TYPE => component(builder, builder.mark())
      case NEWLINE => builder.remapCurrentToken(WHITE_SPACE); builder.advanceLexer()
      case _ => builder.advanceLexer()
    }
  }

  @tailrec
  private def keyValuePair(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.getTokenType match {
      case KEY => builder.advanceLexer(); keyValuePair(builder, marker)
      case COLON => builder.advanceLexer(); keyValuePair(builder, marker)
      case WHITE_SPACE | VALUE_LINE | NEWLINE =>
        value(builder, builder.mark())
        marker.done(KEY_VALUE_PAIR)
      case _ =>
        builder.remapCurrentToken(BAD_CHARACTER)
        marker.done(KEY_VALUE_PAIR)
    }
  }

  @tailrec
  private def value(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.advanceLexer()
    builder.getTokenType match {
      case WHITE_SPACE => value(builder, marker)
      case VALUE_LINE => value(builder, marker)
      case NEWLINE => builder.remapCurrentToken(WHITE_SPACE); value(builder, marker)
      case _ => marker.done(VALUE)
    }
  }

  @tailrec
  private def component(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.getTokenType match {
      case COMPONENT_TYPE | COMPONENT_NAME => builder.advanceLexer(); component(builder, marker)
      case WHITE_SPACE => builder.advanceLexer(); component(builder, marker)
      case KEY => keyValuePair(builder, builder.mark()); component(builder, marker)
      case _ => marker.done(COMPONENT)
    }
  }
}
