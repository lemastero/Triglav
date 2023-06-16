name := "Triglav"

description := "Tremble FP mortals as Trzygłów is walking among us!"

version := "0.0.1"

scalaVersion := "2.13.8"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val catsVersion = "2.9.0"
lazy val catsMtlVersion = "1.2.1"
lazy val scalaTestPlusVersion = "3.1.0.0-RC2"
lazy val scalacheckVersion = "1.14.3"
lazy val zioVersion = "2.0.15"
lazy val silencerVersion = "1.17.13"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-prelude" % "0.0.0+369-a72a24af-SNAPSHOT",
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
  "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
)

addCompilerPlugin(
  "org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
