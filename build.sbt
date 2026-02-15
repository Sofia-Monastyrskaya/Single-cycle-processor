ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

val spinalVersion = "1.12.2"

lazy val root = (project in file("."))
  .settings(
    name := "single_cycle_processor",

    libraryDependencies ++= Seq(
      "com.github.spinalhdl" %% "spinalhdl-core" % spinalVersion,
      "com.github.spinalhdl" %% "spinalhdl-lib"  % spinalVersion,
      "com.github.spinalhdl" %% "spinalhdl-sim"  % spinalVersion 
    ) ++ Seq(
      compilerPlugin("com.github.spinalhdl" %% "spinalhdl-idsl-plugin" % spinalVersion)
    ),

    fork := true,
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:reflectiveCalls"
    )
  )