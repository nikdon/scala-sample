lazy val scalaSample = project
  .in(file("."))
  .settings(libraryDependencies ++= Dependencies.all)
  .settings(Seq(
    fork in run := true,
    javaOptions ++= Seq(
      "-Xms256m",
      "-Xmx2G"
    )
  ))

initialCommands := """|import com.github.nikdon.sample._
                      |""".stripMargin