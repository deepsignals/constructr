libraryDependencies ++= Vector(
  Library.akkaCluster,
  Library.akkaLog4j            % "test",
  Library.akkaMultiNodeTestkit % "test",
  Library.akkaTestkit          % "test",
  Library.log4jCore            % "test",
  Library.mockitoCore          % "test",
  Library.scalaTest            % "test"
)

initialCommands := """|import de.heikoseeberger.constructr._""".stripMargin

unmanagedSourceDirectories.in(MultiJvm) := Vector(scalaSource.in(MultiJvm).value)

test.in(Test) := {
  val t = test.in(Test).value
  test.in(MultiJvm).value
  t
}

inConfig(MultiJvm)(reformatOnCompileSettings)
inConfig(MultiJvm) {
  compileInputs.in(compile) := {
    scalafmt.value
    compileInputs.in(compile).value
  }
}

AutomateHeaderPlugin.automateFor(Compile, Test, MultiJvm)
HeaderPlugin.settingsFor(Compile, Test, MultiJvm)
