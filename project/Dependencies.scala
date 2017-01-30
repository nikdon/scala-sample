import sbt._

object Version {
  final val Shapeless = "2.3.2"
  final val ScalaTest = "3.0.1"

  final val Scala     = "2.12.1"
}
object Library {
  val shapeless = "com.chuusai"   %% "shapeless"  % Version.Shapeless
  val scalaTest = "org.scalatest" %% "scalatest"  % Version.ScalaTest
}

object Dependencies {

  import Library._

  val all = Vector(
    shapeless,
    scalaTest % "test"
  )
}