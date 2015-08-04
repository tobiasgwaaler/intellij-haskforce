package com.haskforce.cabal.packageDescription

sealed trait Language
object Language {
  case object Haskell98 extends Language
  case object Haskell2010 extends Language
  sealed case class Unknown(value: String) extends Language
}

sealed trait Extension
object Extension {
  sealed case class Enable(ext: KnownExtension) extends Extension
  sealed case class Disable(ext: KnownExtension) extends Extension
  sealed case class Unknown(value: String) extends Extension
}

sealed trait KnownExtension
object KnownExtension {
  case object OverlappingInstances extends KnownExtension
  case object UndecidableInstances extends KnownExtension
  case object IncoherentInstances extends KnownExtension
  case object DoRec extends KnownExtension
  case object RecursiveDo extends KnownExtension
  case object ParallelListComp extends KnownExtension
  case object MultiParamTypeClasses extends KnownExtension
  case object MonomorphismRestriction extends KnownExtension
  case object FunctionalDependencies extends KnownExtension
  case object Rank2Types extends KnownExtension
  case object RankNTypes extends KnownExtension
  case object PolymorphicComponents extends KnownExtension
  case object ExistentialQuantification extends KnownExtension
  case object ScopedTypeVariables extends KnownExtension
  case object PatternSignatures extends KnownExtension
  case object ImplicitParams extends KnownExtension
  case object FlexibleContexts extends KnownExtension
  case object FlexibleInstances extends KnownExtension
  case object EmptyDataDecls extends KnownExtension
  case object CPP extends KnownExtension
  case object KindSignatures extends KnownExtension
  case object BangPatterns extends KnownExtension
  case object TypeSynonymInstances extends KnownExtension
  case object TemplateHaskell extends KnownExtension
  case object ForeignFunctionInterface extends KnownExtension
  case object Arrows extends KnownExtension
  case object Generics extends KnownExtension
  case object ImplicitPrelude extends KnownExtension
  case object NamedFieldPuns extends KnownExtension
  case object PatternGuards extends KnownExtension
  case object GeneralizedNewtypeDeriving extends KnownExtension
  case object ExtensibleRecords extends KnownExtension
  case object RestrictedTypeSynonyms extends KnownExtension
  case object HereDocuments extends KnownExtension
  case object MagicHash extends KnownExtension
  case object TypeFamilies extends KnownExtension
  case object StandaloneDeriving extends KnownExtension
  case object UnicodeSyntax extends KnownExtension
  case object UnliftedFFITypes extends KnownExtension
  case object InterruptibleFFI extends KnownExtension
  case object CApiFFI extends KnownExtension
  case object LiberalTypeSynonyms extends KnownExtension
  case object TypeOperators extends KnownExtension
  case object RecordWildCards extends KnownExtension
  case object RecordPuns extends KnownExtension
  case object DisambiguateRecordFields extends KnownExtension
  case object TraditionalRecordSyntax extends KnownExtension
  case object OverloadedStrings extends KnownExtension
  case object GADTs extends KnownExtension
  case object GADTSyntax extends KnownExtension
  case object MonoPatBinds extends KnownExtension
  case object RelaxedPolyRec extends KnownExtension
  case object ExtendedDefaultRules extends KnownExtension
  case object UnboxedTuples extends KnownExtension
  case object DeriveDataTypeable extends KnownExtension
  case object DeriveGeneric extends KnownExtension
  case object DefaultSignatures extends KnownExtension
  case object InstanceSigs extends KnownExtension
  case object ConstrainedClassMethods extends KnownExtension
  case object PackageImports extends KnownExtension
  case object ImpredicativeTypes extends KnownExtension
  case object NewQualifiedOperators extends KnownExtension
  case object PostfixOperators extends KnownExtension
  case object QuasiQuotes extends KnownExtension
  case object TransformListComp extends KnownExtension
  case object MonadComprehensions extends KnownExtension
  case object ViewPatterns extends KnownExtension
  case object XmlSyntax extends KnownExtension
  case object RegularPatterns extends KnownExtension
  case object TupleSections extends KnownExtension
  case object GHCForeignImportPrim extends KnownExtension
  case object NPlusKPatterns extends KnownExtension
  case object DoAndIfThenElse extends KnownExtension
  case object MultiWayIf extends KnownExtension
  case object LambdaCase extends KnownExtension
  case object RebindableSyntax extends KnownExtension
  case object ExplicitForAll extends KnownExtension
  case object DatatypeContexts extends KnownExtension
  case object MonoLocalBinds extends KnownExtension
  case object DeriveFunctor extends KnownExtension
  case object DeriveTraversable extends KnownExtension
  case object DeriveFoldable extends KnownExtension
  case object NondecreasingIndentation extends KnownExtension
  case object SafeImports extends KnownExtension
  case object Safe extends KnownExtension
  case object Trustworthy extends KnownExtension
  case object Unsafe extends KnownExtension
  case object ConstraintKinds extends KnownExtension
  case object PolyKinds extends KnownExtension
  case object DataKinds extends KnownExtension
  case object ParallelArrays extends KnownExtension
  case object RoleAnnotations extends KnownExtension
  case object OverloadedLists extends KnownExtension
  case object EmptyCase extends KnownExtension
  case object AutoDeriveTypeable extends KnownExtension
  case object NegativeLiterals extends KnownExtension
  case object NumDecimals extends KnownExtension
  case object NullaryTypeClasses extends KnownExtension
  case object ExplicitNamespaces extends KnownExtension
  case object AllowAmbiguousTypes extends KnownExtension
}
