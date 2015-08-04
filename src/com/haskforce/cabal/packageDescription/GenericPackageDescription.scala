package com.haskforce.cabal.packageDescription

/**
 * Mirrors the data type from Cabal's Distribution.PackageDescription
 */
sealed case class GenericPackageDescription(
  packageDescription: PackageDescription,
  genPackageFlags: Seq[Flag],
  condLibrary: Option[Cond.Tree[Library]],
  condExecutables: Cond.Map[Executable],
  condTestSuites: Cond.Map[TestSuite],
  condBenchmarks: Cond.Map[Benchmark]
)

object Cond {
  type Tree[A] = CondTree[ConfVar, Seq[Dependency], A]
  type Map[A] = Seq[(String, Tree[A])]
}

sealed case class PackageDescription(
  package_ : PackageIdentifier,
  license: License,
  licenseFile: FilePath,
  copyright: String,
  maintainer: String,
  author: String,
  stability: String,
  testedWith: Seq[(CompilerFlavor, VersionRange)],
  homepage: String,
  pkgUrl: String,
  bugReports: String,
  sourceRepos: Seq[SourceRepo],
  synopsis: String,
  description: String,
  category: String,
  customFieldsPD: Seq[(String, String)],
  buildDepends: Seq[Dependency],
  specVersionRaw: Either[Version, VersionRange],
  buildType: Option[BuildType],
  library: Option[Library],
  executables: Seq[Executable],
  testSuites: Seq[TestSuite],
  benchmarks: Seq[Benchmark],
  dataFiles: Seq[FilePath],
  dataDir: FilePath,
  extraSrcFiles: Seq[FilePath],
  extraTmpFiles: Seq[FilePath],
  extraDocFiles: Seq[FilePath]
)

sealed case class FilePath(value: String)

sealed case class PackageIdentifier(name: PackageName, version: Version)

sealed case class PackageName(value: String)

sealed case class Version(branch: Seq[Int], tags: Seq[String])

sealed trait License
object License {
  case object GPL extends License
  case object AGPL extends License
  case object LGPL extends License
  case object BSD3 extends License
  case object BSD4 extends License
  case object MIT extends License
  sealed case class Apache(version: Option[Version]) extends License
  case object PublicDomain extends License
  case object AllRightsReserved extends License
  case object Other extends License
  sealed case class Unknown(name: String) extends License
}

sealed trait CompilerFlavor
object CompilerFlavor {
  case object NHC extends CompilerFlavor
  case object YHC extends CompilerFlavor
  case object Hugs extends CompilerFlavor
  case object HBC extends CompilerFlavor
  case object Helium extends CompilerFlavor
  case object JHC extends CompilerFlavor
  case object LHC extends CompilerFlavor
  case object UHC extends CompilerFlavor
  sealed case class OtherCompiler(name: String) extends CompilerFlavor
}

sealed trait VersionRange
object VersionRange {
  case object AnyVersion extends VersionRange
  sealed case class ThisVersion(version: Version) extends VersionRange
  sealed case class LaterVersion(version: Version) extends VersionRange
  sealed case class EarlierVersion(version: Version) extends VersionRange
  sealed case class WildcardVersion(version: Version) extends VersionRange
  sealed case class UnionVersionRanges(_1: VersionRange, _2: VersionRange) extends VersionRange
  sealed case class IntersectVersionRanges(_1: VersionRange, _2: VersionRange) extends VersionRange
  sealed case class VersionRangeParens(range: VersionRange) extends VersionRange
}

sealed case class SourceRepo(
  kind: RepoKind,
  type_ : Option[RepoType],
  location: Option[String],
  module: Option[String],
  branch: Option[String],
  tag: Option[String],
  subdir: Option[FilePath]
)

sealed trait RepoKind
object RepoKind {
  case object Head extends RepoKind
  case object This extends RepoKind
  sealed case class Unknown(value: String)
}

sealed trait RepoType
object RepoType {
  case object Darcs extends RepoType
  case object Git extends RepoType
  case object SVN extends RepoType
  case object CVS extends RepoType
  case object Mercurial extends RepoType
  case object GnuArch extends RepoType
  case object Bazaar extends RepoType
  case object Monotone extends RepoType
  sealed case class OtherRepoType(value: String) extends RepoType
}

sealed case class Dependency(packageName: PackageName, versionRange: VersionRange)

sealed trait BuildType
object BuildType {
  case object Simple extends BuildType
  case object Configure extends BuildType
  case object Custom extends BuildType
  sealed case class Unknown(value: String) extends BuildType
}

sealed case class Library(
  exposedModules: Seq[ModuleName],
  libExposed: Boolean,
  libBuildInfo: BuildInfo
)

sealed case class ModuleName(names: Seq[String])

sealed case class BuildInfo(
  buildable: Boolean,
  buildTools: Seq[Dependency],
  cppOptions: Seq[String],
  ccOptions: Seq[String],
  ldOptions: Seq[String],
  pkgconfigDepends: Seq[Dependency],
  frameworks: Seq[String],
  cSources: Seq[FilePath],
  hsSourceDirs: Seq[FilePath],
  otherModules: Seq[ModuleName],
  defaultLanguage: Option[Language],
  otherLanguages: Seq[Language],
  defaultExtensions: Seq[Extension],
  otherExtensions: Seq[Extension],
  oldExtensions: Seq[Extension],
  extraLibs: Seq[String],
  extraLibDirs: Seq[String],
  includeDirs: Seq[FilePath],
  includes: Seq[FilePath],
  installIncludes: Seq[FilePath],
  options: Seq[(CompilerFlavor, Seq[String])],
  ghcProfOptions: Seq[String],
  ghcSharedOptions: Seq[String],
  customFieldsBI: Seq[(String, String)],
  targetBuildDepends: Seq[Dependency]
)

sealed case class Executable(name: String, modulePath: FilePath, buildInfo: BuildInfo)

sealed case class TestSuite(name: String, interface: TestSuiteInterface, buildInfo: BuildInfo, enable: Boolean)

sealed trait TestSuiteInterface
object TestSuiteInterface {
  sealed case class ExeV10(version: Version, filePath: FilePath) extends TestSuiteInterface
  sealed case class LibV09(version: Version, moduleName: ModuleName) extends TestSuiteInterface
  sealed case class Unsupported(testType: TestType) extends TestSuiteInterface
}

sealed trait TestType
object TestType {
  sealed case class Exe(version: Version) extends TestType
  sealed case class Lib(version: Version) extends TestType
  sealed case class Unknown(name: String, version: Version) extends TestType
}

sealed case class Benchmark(
  name: String,
  interface: BenchmarkInterface,
  buildInfo: BuildInfo,
  enabled: Boolean
)

sealed trait BenchmarkInterface
object BenchmarkInterface {
  sealed case class ExeV10(version: Version, filePath: FilePath) extends BenchmarkInterface
  sealed case class Unsupported(type_ : BenchmarkType) extends BenchmarkInterface
}

sealed trait BenchmarkType
object BenchmarkType {
  sealed case class Exe(version: Version) extends BenchmarkType
  sealed case class Unknown(name: String, version: Version) extends BenchmarkType
}

sealed case class Flag(
  name: FlagName,
  description: String,
  default: Boolean,
  manual: Boolean
)

sealed case class FlagName(value: String)

sealed case class CondTree[V, C, A](
  data: A,
  constraints: C,
  components: Seq[(Condition[V], CondTree[V, C, A], Option[CondTree[V, C, A]])]
)

sealed trait Condition[+C]
object Condition {
  sealed case class Var[+C](value: C) extends Condition[C]
  sealed case class Lit(value: Boolean) extends Condition[Nothing]
  sealed case class Not[+C](condition: Condition[C]) extends Condition[C]
  sealed case class Or[+C](_1: Condition[C], _2: Condition[C]) extends Condition[C]
  sealed case class And[+C](_1: Condition[C], _2: Condition[C]) extends Condition[C]
}

sealed trait ConfVar
object ConfVar {
  sealed case class OS(type_ : OSType) extends ConfVar
  sealed case class Arch(type_ : ArchType) extends ConfVar
  sealed case class Flag(name: FlagName) extends ConfVar
  sealed case class Impl(compilerFlavor: CompilerFlavor, versionRange: VersionRange) extends ConfVar
}

sealed trait OSType
object OSType {
  case object Linux extends OSType
  case object Windows extends OSType
  case object OSX extends OSType
  case object FreeBSD extends OSType
  case object OpenBSD extends OSType
  case object NetBSD extends OSType
  case object Solaris extends OSType
  case object AIX extends OSType
  case object HPUX extends OSType
  case object IRIX extends OSType
  case object HaLVM extends OSType
  case object IOS extends OSType
  sealed case class Other(value: String) extends OSType
}

sealed trait ArchType
object ArchType {
  case object I386 extends ArchType
  case object X86_64 extends ArchType
  case object PPC extends ArchType
  case object PPC64 extends ArchType
  case object Sparc extends ArchType
  case object Arm extends ArchType
  case object Mips extends ArchType
  case object SH extends ArchType
  case object IA64 extends ArchType
  case object S390 extends ArchType
  case object Alpha extends ArchType
  case object Hppa extends ArchType
  case object Rs6000 extends ArchType
  case object M68k extends ArchType
  case object Vax extends ArchType
  sealed case class Other(value: String) extends ArchType
}
