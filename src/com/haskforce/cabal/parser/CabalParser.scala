package com.haskforce.cabal.parser

import com.haskforce.cabal.psi.CabalFieldType
import com.haskforce.cabal.psi.CabalTypes._
import com.intellij.lang.{ASTNode, PsiBuilder, PsiParser}
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NotNull

import scala.annotation.tailrec

class CabalParser extends PsiParser {
  @NotNull
  override def parse(root: IElementType, builder: PsiBuilder): ASTNode = {
    parse(root, builder, builder.mark())
    builder.getTreeBuilt
  }

  @tailrec
  private def parse(root: IElementType, builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    if (builder.eof) {
      marker.done(root)
      return
    }
    builder.getTokenType match {
      case KEY => keyValuePair(builder)
      case COMPONENT_TYPE => component(builder, builder.mark())
      case NEWLINE =>
        builder.remapCurrentToken(WHITE_SPACE)
        builder.advanceLexer()
      case COMMENT => builder.advanceLexer()
      case _ =>
        builder.remapCurrentToken(BAD_CHARACTER)
        builder.advanceLexer()
    }
    parse(root, builder, marker)
  }

  private def keyValuePair(builder: PsiBuilder): Unit = {
    builder.mark().done(
      builder.getTokenText.toLowerCase match {
        case "name" => loop(PACKAGE_NAME, freeform)
        case "version" => loop(PACKAGE_VERSION, numbers)
        case _ => loop(CUSTOM_FIELD, freeform)
      }
    )

    @tailrec
    def loop(keyType: CabalFieldType, valueParser: PsiBuilder => Unit): CabalFieldType = {
      builder.getTokenType match {
        case KEY => builder.advanceLexer(); loop(keyType, valueParser)
        case COLON => builder.advanceLexer(); loop(keyType, valueParser)
        case _ => valueParser(builder); keyType
      }
    }
  }

  private def freeform(builder: PsiBuilder): Unit = {
    val outer = builder.mark()
    if (builder.getTokenType == WHITE_SPACE) builder.advanceLexer()
    loop()
    outer.done(FREEFORM)

    @tailrec
    def loop(): Unit = {
      if (builder.eof) return
      val t = builder.getTokenType
      if (t == NEWLINE) {
        builder.remapCurrentToken(WHITE_SPACE)
        builder.advanceLexer()
        if (builder.getTokenType != WHITE_SPACE) return
        else builder.advanceLexer()
      } else {
        val marker = builder.mark()
        while (!builder.eof && builder.getTokenType != NEWLINE) builder.advanceLexer()
        marker.collapse(VALUE)
      }
      loop()
    }
  }

  private def numbers(builder: PsiBuilder): Unit = {
    val outer = builder.mark()
    if (builder.getTokenType == WHITE_SPACE) builder.advanceLexer()
    builder.getTokenType match {
      case NUMBER =>
        val marker = builder.mark()
        builder.advanceLexer()
        loop(marker, NUMBER)
      case _ =>
        builder.error("Expected numbers")
    }
    outer.done(NUMBERS)

    @tailrec
    def loop(marker: PsiBuilder.Marker, last: IElementType): Unit = {
      if (builder.eof) return
      (last, builder.getTokenType) match {
        case (NUMBER, DOT) =>
          builder.advanceLexer()
          loop(marker, builder.getTokenType)
        case (DOT, NUMBER) =>
          builder.advanceLexer()
          loop(marker, builder.getTokenType)
        case (NUMBER, NEWLINE) =>
          marker.done(VALUE)
        case _ =>
          while (!builder.eof && builder.getTokenType != NEWLINE) builder.advanceLexer()
          marker.error("Expected numbers")
      }
    }
  }

  @tailrec
  private def value(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.advanceLexer()
    builder.getTokenType match {
      case WHITE_SPACE => value(builder, marker)
      case VALUE_WORD => value(builder, marker)
      case NEWLINE =>
        builder.remapCurrentToken(WHITE_SPACE)
        value(builder, marker)
      case _ => marker.done(VALUE)
    }
  }

  @tailrec
  private def component(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.getTokenType match {
      case COMPONENT_TYPE =>
        componentType(builder)
        component(builder, marker)
      case WHITE_SPACE =>
        builder.advanceLexer()
        component(builder, marker)
      case COMPONENT_NAME =>
        componentName(builder)
        component(builder, marker)
      case INDENT =>
        componentBody(builder, builder.mark())
        marker.done(COMPONENT)
      case KEY =>
        keyValuePair(builder)
        component(builder, marker)
      case NEWLINE =>
        builder.remapCurrentToken(WHITE_SPACE)
        builder.advanceLexer()
        component(builder, marker)
      case _ =>
        builder.remapCurrentToken(BAD_CHARACTER)
        builder.advanceLexer()
        marker.done(COMPONENT)
    }
  }

  private def componentType(builder: PsiBuilder): Unit = {
    val marker = builder.mark()
    builder.advanceLexer()
    marker.done(COMPONENT_TYPE)
  }

  private def componentName(builder: PsiBuilder): Unit = {
    val marker = builder.mark()
    builder.advanceLexer()
    marker.done(COMPONENT_NAME)
  }

  @tailrec
  private def componentBody(builder: PsiBuilder, marker: PsiBuilder.Marker): Unit = {
    builder.getTokenType match {
      case COMMENT =>
        builder.advanceLexer()
        componentBody(builder, marker)
      case NEWLINE =>
        builder.remapCurrentToken(WHITE_SPACE)
        builder.advanceLexer()
        componentBody(builder, marker)
      case EMPTY =>
        builder.advanceLexer()
        componentBody(builder, marker)
      case INDENT =>
        builder.advanceLexer()
        keyValuePair(builder)
        componentBody(builder, marker)
      case _ =>
        marker.done(COMPONENT_BODY)
    }
  }
}
