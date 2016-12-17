package com.github.nikdon.sample

import shapeless.{ :+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy, Nat, ops }

import scala.annotation.implicitNotFound
import scala.language.higherKinds
import scala.util.Random

@implicitNotFound("Sample[${A}] can not be created because some type class instances are not in scope or not devised.")
trait Sample[A] {
  /** Returns a sample object of type 'A'. */
  def get: A

  /** Returns n sample objects of type 'A'. If n <= 0, returns an empty list. */
  def getN(n: Int)(implicit sample: Sample[A]): List[A] = {
    def go(n: Int, acc: List[A] = List.empty): List[A] =
      if (n <= 0) acc
      else go(n - 1, sample.get :: acc)

    go(n)
  }
}

object Sample {
  def apply[A](implicit sample: Sample[A]): Sample[A] = sample

  def instance[A](func: () => A): Sample[A] = new Sample[A] {
    override def get: A = func()
  }

  implicit val booleanSample = new Sample[Boolean] {
    override def get: Boolean = Random.nextBoolean()
  }

  implicit val intSample = new Sample[Int] {
    override def get: Int = Random.nextInt()
  }

  implicit val longSample = new Sample[Long] {
    override def get: Long = Random.nextLong()
  }

  implicit val floatSample = new Sample[Float] {
    override def get: Float = Random.nextFloat()
  }

  implicit val doubleSample = new Sample[Double] {
    override def get: Double = Random.nextDouble()
  }

  implicit val charSample = new Sample[Char] {
    override def get: Char = Random.nextPrintableChar()
  }

  implicit val stringSample = new Sample[String] {
    override def get: String = Random.nextString(4)
  }

  implicit def mapSample[A: Sample, B: Sample] = new Sample[Map[A, B]] {
    override def get: Map[A, B] = Map(Sample[A].get -> Sample[B].get)
  }

  implicit val hnilSample: Sample[HNil] = instance(() => HNil)

  implicit def hlistSample[H, T <: HList](
      implicit hSample: Lazy[Sample[H]],
      tSample: Sample[T]
  ): Sample[H :: T] = instance(() => hSample.value.get :: tSample.get)

  implicit val cnilSample: Sample[CNil] = instance(() => throw new Exception("Inconceivable!"))

  implicit def coproductSample[H, T <: Coproduct, N <: Nat](
      implicit hSample: Lazy[Sample[H]],
      tSample: Sample[T],
      tLength: ops.coproduct.Length.Aux[T, N],
      tLengthAsInt: ops.nat.ToInt[N]
  ): Sample[H :+: T] = new Sample[H :+: T] {
    override def get: :+:[H, T] = {
      val length  = 1 + tLengthAsInt()
      val chooseH = scala.util.Random.nextDouble < (1.0 / length)
      if (chooseH) Inl(hSample.value.get) else Inr(tSample.get)
    }
  }

  implicit def genericSample[A, R](
      implicit gen: Generic.Aux[A, R],
      sample: Lazy[Sample[R]]
  ): Sample[A] = instance(() => gen.from(sample.value.get))
}
