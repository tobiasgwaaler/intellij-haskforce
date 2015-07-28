package com.haskforce.cabal.lexer

/**
 * Cabal lexer test suite.
 */
//noinspection UnitMethodIsParameterless,EmptyParenMethodAccessedAsParameterless
class CabalLexerTest extends CabalLexerTestBase {
  override val OVERWRITE = true // TODO: Remove me
  def testSimple = doTest
}
