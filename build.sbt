name := "Triglav"

description := "Tremble FP mortals as Trzygłów is walking among us!"

version := "0.0.1"

scalaVersion := "2.13.3"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val catsVersion = "2.3.1"
lazy val catsMtlVersion = "1.1.1"
lazy val scalaTestPlusVersion = "3.1.0.0-RC2"
lazy val scalacheckVersion = "1.14.3"
lazy val zioVersion = "1.0.4"
lazy val silencerVersion = "1.7.2"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless"    % "2.3.3",

  // cats
  "org.typelevel" %% "cats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-free" % catsVersion withSources(),
  "org.typelevel" %% "cats-laws" % catsVersion withSources(),
  "org.typelevel" %% "alleycats-core" % catsVersion withSources(),
  "org.typelevel" %% "cats-mtl" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-mtl-laws" % catsMtlVersion withSources(),
  "org.typelevel" %% "cats-effect" % "2.4.2" withSources(),

  "org.scalaz"    %% "scalaz-core" % "7.3.3" withSources(),

  "dev.zio"     %% "zio"          % zioVersion,
  "dev.zio"     %% "zio-prelude"  % "0.0.0+369-a72a24af-SNAPSHOT" withSources(),
  "dev.zio"     %% "zio-test"     % zioVersion % Test,
  "dev.zio"     %% "zio-test-sbt" % zioVersion % Test,

  compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.3" cross CrossVersion.full)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
