package com.haskforce.cabal.lexer

import java.io.{File, FileNotFoundException, IOException}

import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.rt.execution.junit.FileComparisonFailure
import com.intellij.testFramework.{LexerTestCase, TestDataFile, VfsTestUtil}
import junit.framework.AssertionFailedError
import org.jetbrains.annotations.NonNls

/**
 * Provides a simple interface for writing tests for the Cabal lexer.
 */
class CabalLexerTestBase extends LexerTestCase {
  val OVERWRITE = false
  val srcPath = FileUtil.join(getDirPath, "parser")
  val expectedPath = FileUtil.join(getDirPath, "lexer")
  override def getDirPath = FileUtil.join("tests", "gold", "cabal")
  override def createLexer() = new CabalLexer()

  def doTest(): Unit = {
    val testName = getTestName(false)
    val fileName = testName + ".cabal"
    val text: String = try { loadFile(fileName) } catch {
      case e: IOException => fail(s"Can't load file $fileName: ${e.getMessage}")
    }
    val result = printTokens(text, 0)
    try {
      doCheckResult(FileUtil.join(expectedPath, "expected"), testName + ".txt", result)
    } catch {
      case e: IOException => fail("Unexpected IO Exception: " + e.getMessage)
    }
  }

  protected def loadFile(@NonNls @TestDataFile name: String): String = doLoadFile(srcPath, name)

  private def doLoadFile(myFullDataPath: String, name: String): String = {
    StringUtil.convertLineSeparators(FileUtil.loadFile(new File(myFullDataPath, name), CharsetToolkit.UTF8).trim())
  }

  private def doCheckResult(fullPath: String, targetDataName: String, rawText: String): Unit = {
    val text = rawText.trim()
    val expectedFileName = FileUtil.join(fullPath, targetDataName)
    if (OVERWRITE) {
      VfsTestUtil.overwriteTestData(expectedFileName, text)
      System.out.println(s"File $expectedFileName created.")
    }
    try {
      val expectedText = doLoadFile(fullPath, targetDataName)
      if (expectedText != text) throw new FileComparisonFailure(targetDataName, expectedText, text, expectedFileName)
    } catch {
      case e: FileNotFoundException =>
        VfsTestUtil.overwriteTestData(expectedFileName, text)
        fail(s"No output text found, file $expectedFileName created.")
    }
  }

  /**
   * Similar to TestCase.fail except is polymorphic in return type.
   */
  def fail[A](message: String): A = throw new AssertionFailedError(message)
}
