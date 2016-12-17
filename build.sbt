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

initialCommands := """|import io.circe._
                      |import io.circe.syntax._
                      |import io.circe.generic.auto._
                      |""".stripMargin