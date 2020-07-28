name := "Triglav"

description := "Tremble FP mortals as Trzygłów is walking among us!"

version := "0.0.1"

scalaVersion := "2.13.3"

val zioVersion = "1.0.0-RC21-2"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio"             % zioVersion,
  "dev.zio" %% "zio-test"        % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt"    % zioVersion % Test
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

scalacOptions ++= Seq(
  "-encoding", "UTF-8"
)
