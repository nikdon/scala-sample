package com.github.nikdon.sample

import java.time.Instant

import org.scalatest.{ FlatSpec, Matchers }

class SampleTest extends FlatSpec with Matchers {

  sealed trait Foo
  case class Fiz(fiz: String) extends Foo
  case class Buz(buz: String) extends Foo

  case class SomeEvent(happened: Instant)

  behavior of "Sample"

  it should "return a sample object" in {
    Sample[Foo].get shouldBe a[Foo]
  }

  it should "return a sample object with custom implicit sample definition" in {
    implicit val dateSample = new Sample[Instant] {
      override def get: Instant = Instant.now
    }

    Sample[SomeEvent].get shouldBe a[SomeEvent]
  }

  it should "return an optional sample object" in {
    Sample[Option[Foo]].get shouldBe a [Option[_]]
  }

  it should "return an either sample object" in {
    Sample[Either[Fiz, Buz]].get shouldBe a [Either[_, _]]
  }

  it should "return 5 samples" in {
    Sample[Foo].getN(5).size shouldBe 5
  }
}
