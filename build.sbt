name := "Triglav"

description := "Tremble FP mortals as Trzygłów is walking among us!"

version := "0.0.1"

scalaVersion := "2.13.15"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val catsVersion = "2.13.0"
lazy val scalacheckVersion = "1.14.3"
lazy val zioVersion = "1.0.18"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-prelude" % "0.0.0+369-a72a24af-SNAPSHOT",
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test
)

addCompilerPlugin(
  "org.typelevel" %% "kind-projector" % "0.13.3" cross CrossVersion.full
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
