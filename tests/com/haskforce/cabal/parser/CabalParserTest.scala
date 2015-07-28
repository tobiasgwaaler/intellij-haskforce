package com.haskforce.cabal.parser

/**
 * Cabal parser test suite.
 */
//noinspection UnitMethodIsParameterless,EmptyParenMethodAccessedAsParameterless
class CabalParserTest extends CabalParserTestBase {
  override val OVERWRITE = true // TODO: Remove me
  def testSimple = doTest
}
