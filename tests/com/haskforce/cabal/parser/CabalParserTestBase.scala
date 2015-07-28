package com.haskforce.cabal.parser

import java.io.{IOException, FileNotFoundException, File}

import com.intellij.openapi.fileEditor.impl.LoadTextUtil
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.psi.PsiFile
import com.intellij.rt.execution.junit.FileComparisonFailure
import com.intellij.testFramework.{LightVirtualFile, VfsTestUtil, ParsingTestCase}
import junit.framework.AssertionFailedError

/**
 * Provides a simple interface for writing tests for the Cabal parser.
 */
class CabalParserTestBase extends ParsingTestCase("parser", "cabal", new CabalParserDefinition) {
  import junit.framework.TestCase._

  /**
   * Override this with `true` to simply overwrite the expected output files with the parse results.
   */
  val OVERWRITE = false

  override def getTestDataPath: String = FileUtil.join("tests", "gold", "cabal")

  /**
   * Basically translated from ParsingTestCase so we can use our own checkResult method.
   */
  def doTest(): Unit = {
    val name = getTestName(false)
    try {
      val text = loadFile(name + "." + myFileExt)
      myFile = createPsiFile(name, text)
      ParsingTestCase.ensureParsed(myFile)
      val lightFile = myFile.getVirtualFile.asInstanceOf[LightVirtualFile]
      assertEquals("light virtual file text mismatch", text, lightFile.getContent.toString)
      assertEquals("virtual file text mismatch", text, LoadTextUtil.loadText(myFile.getVirtualFile))
      assertEquals("doc text mismatch", text, myFile.getViewProvider.getDocument.getText)
      assertEquals("psi text mismatch", text, myFile.getText)
      checkResult(name, myFile)
    } catch {
      case e: IOException => throw new RuntimeException(e)
    }
  }

  /**
   * Composes our doTest and assertSameLinesWithFile methods.
   */
  override def checkResult(targetDataName: String, file: PsiFile): Unit = {
    val expectedFileName = FileUtil.join(myFullDataPath, "expected", targetDataName + ".txt")
    val text = ParsingTestCase.toParseTreeText(file, skipSpaces, includeRanges()).trim()
    assertSameLinesWithFile(expectedFileName, text)
  }

  /**
   * Basically translated from UsefulTestCase.assertSameLinesWithFile
   * Provided so we can OVERWRITE the expected output.
   */
  def assertSameLinesWithFile(filePath: String, actualText: String, trimBeforeComparing: Boolean = true): Unit = {
    var fileText: String = ""
    try {
      if (OVERWRITE) {
        VfsTestUtil.overwriteTestData(filePath, actualText)
        System.out.println(s"File $filePath created.")
      }
      fileText = FileUtil.loadFile(new File(filePath), CharsetToolkit.UTF8_CHARSET)
    } catch {
      case e: FileNotFoundException =>
        VfsTestUtil.overwriteTestData(filePath, actualText)
        throw new AssertionFailedError(s"No output text found, file $filePath created.")
      case e: IOException => throw new RuntimeException(e)
    }
    val expected = StringUtil.convertLineSeparators(if (trimBeforeComparing) fileText.trim() else fileText)
    val actual = StringUtil.convertLineSeparators(if (trimBeforeComparing) actualText.trim() else actualText)
    if (expected != actual) throw new FileComparisonFailure(null, expected, actual, filePath)
  }
}
