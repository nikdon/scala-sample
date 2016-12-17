import org.scalafmt.sbt.ScalaFmtPlugin
import org.scalafmt.sbt.ScalaFmtPlugin.autoImport._
import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object Build extends AutoPlugin {

  override def requires = JvmPlugin && ScalaFmtPlugin
  override def trigger  = allRequirements

  override def projectSettings =
    reformatOnCompileSettings ++
//      addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full) ++
      Vector(
        // Compile settings
        scalaVersion := Version.Scala,

        crossScalaVersions := Vector(scalaVersion.value),

        scalacOptions ++= Vector(
          "-encoding",
          "utf-8",
          "-target:jvm-1.8",
          "-deprecation",
          "-feature",
          "-unchecked",
          "-Xlint",
          "-Xfatal-warnings",
          "-Ywarn-adapted-args",
          "-Ywarn-dead-code",
          "-Ywarn-inaccessible",
          "-Ywarn-nullary-override",
          "-Ywarn-numeric-widen"
        ),

        unmanagedSourceDirectories.in(Compile) := Vector(scalaSource.in(Compile).value),
        unmanagedSourceDirectories.in(Test)    := Vector(scalaSource.in(Test).value),

        // Publish settings
        organization := "com.github.nikdon",

        // scalafmt settings
        formatSbtFiles := false,
        scalafmtConfig := Some(baseDirectory.in(ThisBuild).value / ".scalafmt.conf"),

        ivyScala := ivyScala.value.map(_.copy(overrideScalaVersion = sbtPlugin.value)) // TODO Remove once this workaround no longer needed (https://github.com/sbt/sbt/issues/2786)
      )
}
