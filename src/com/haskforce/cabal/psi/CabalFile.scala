package com.haskforce.cabal.psi

import com.haskforce.cabal.CabalFileType
import com.haskforce.cabal.CabalIcons
import com.haskforce.cabal.CabalLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class CabalFile(@NotNull viewProvider: FileViewProvider) extends PsiFileBase(viewProvider, CabalLanguage.INSTANCE) {
  @NotNull override def getFileType = CabalFileType.INSTANCE
  override def toString = "Cabal File"
  @Nullable override def getIcon(flags: Int) = CabalIcons.FILE
}
