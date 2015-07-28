package com.haskforce.cabal.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

object CabalElementFactory {
  def createElement(node: ASTNode): PsiElement = node.getElementType match {
    case CabalTypes.KEY_VALUE_PAIR => CabalKeyValuePair(node)
    case CabalTypes.KEY => CabalKey(node)
    case CabalTypes.VALUE => CabalValue(node)
    case CabalTypes.COMPONENT => CabalComponent(node)
  }
}

sealed trait CabalCompositeElement extends PsiElement

sealed abstract class CabalCompositeElementImpl(node: ASTNode)
extends ASTWrapperPsiElement(node) with CabalCompositeElement {
  override def toString = getNode.getElementType.toString
}

sealed case class CabalKeyValuePair(node: ASTNode) extends CabalCompositeElementImpl(node) {
  def key = findChildByType(CabalTypes.KEY)
  def value = findChildByType(CabalTypes.VALUE)
}

sealed case class CabalKey(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalValue(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalComponent(node: ASTNode) extends CabalCompositeElementImpl(node) {
  def type_ = findChildByType(CabalTypes.COMPONENT_TYPE)
  def name = findChildByType(CabalTypes.COMPONENT_NAME)
  def keyValuePairs = findChildrenByClass(classOf[CabalKeyValuePair])
}
