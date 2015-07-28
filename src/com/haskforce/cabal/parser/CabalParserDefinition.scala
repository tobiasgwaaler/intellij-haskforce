package com.haskforce.cabal.parser

import com.haskforce.cabal.CabalLanguage
import com.haskforce.cabal.lexer.CabalLexer
import com.haskforce.cabal.psi.{CabalElementFactory, CabalFile}
import com.intellij.lang.{ASTNode, ParserDefinition, PsiParser}
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.{FileViewProvider, PsiElement, PsiFile}
import com.intellij.psi.tree.{IFileElementType, TokenSet}
import org.jetbrains.annotations.NotNull

class CabalParserDefinition extends ParserDefinition {
  import ParserDefinition._

  val FILE = new IFileElementType(CabalLanguage.INSTANCE)

  @NotNull override def createLexer(project: Project): Lexer = new CabalLexer()

  override def createParser(project: Project): PsiParser = new CabalParser()

  override def getFileNodeType: IFileElementType = FILE

  @NotNull override def getWhitespaceTokens: TokenSet = TokenSet.EMPTY

  @NotNull override def getCommentTokens: TokenSet = TokenSet.EMPTY

  @NotNull override def getStringLiteralElements: TokenSet = TokenSet.EMPTY

  @NotNull override def createElement(node: ASTNode): PsiElement = CabalElementFactory.createElement(node)

  override def createFile(viewProvider: FileViewProvider): PsiFile = new CabalFile(viewProvider)

  override def spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode) = SpaceRequirements.MAY
}
