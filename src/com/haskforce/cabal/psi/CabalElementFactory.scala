package com.haskforce.cabal.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

import scala.collection.JavaConversions._

object CabalElementFactory {
  def createElement(node: ASTNode): PsiElement = node.getElementType match {
    case CabalTypes.KEY => CabalKey(node)
    case CabalTypes.VALUE => CabalValue(node)

    case CabalTypes.COMPONENT => CabalComponent(node)
    case CabalTypes.COMPONENT_TYPE => CabalComponentType(node)
    case CabalTypes.COMPONENT_NAME => CabalComponentName(node)
    case CabalTypes.COMPONENT_BODY => CabalComponentBody(node)

    case CabalTypes.PACKAGE_NAME => CabalPackageName(node)
    case CabalTypes.PACKAGE_VERSION => CabalPackageVersion(node)
    case CabalTypes.CUSTOM_FIELD => CabalCustomField(node)

    case CabalTypes.NUMBERS => CabalNumbers(node)
    case CabalTypes.FREEFORM => CabalFreeform(node)
  }
}

sealed trait CabalCompositeElement extends PsiElement

sealed abstract class CabalCompositeElementImpl(node: ASTNode)
extends ASTWrapperPsiElement(node) with CabalCompositeElement {
  override def toString = getNode.getElementType.toString
}

sealed case class CabalKey(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalValue(node: ASTNode) extends CabalCompositeElementImpl(node) {
  override def getText: String = {
    findChildrenByType[PsiElement](CabalTypes.VALUE_WORD).map(_.getText).mkString("\n")
  }
}

sealed case class CabalComponent(node: ASTNode) extends CabalCompositeElementImpl(node) {
  def type_ = findChildByClass(classOf[CabalComponentType])
  def name = Option(findChildByClass(classOf[CabalComponentName]))
  def body = findChildByClass(classOf[CabalComponentBody])
}

sealed case class CabalComponentType(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalComponentName(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalComponentBody(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalPackageName(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalPackageVersion(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalCustomField(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalFreeform(node: ASTNode) extends CabalCompositeElementImpl(node)

sealed case class CabalNumbers(node: ASTNode) extends CabalCompositeElementImpl(node)
