# scala-sample

[![Build Status](https://travis-ci.org/nikdon/scala-sample.svg?branch=master)](https://travis-ci.org/nikdon/scala-sample)
[![codecov](https://codecov.io/gh/nikdon/scala-sample/branch/master/graph/badge.svg)](https://codecov.io/gh/nikdon/scala-sample)


**scala-sample** is code snippet for random object generation from [**shapeless guide**](https://github.com/underscoreio/shapeless-guide)
as a library that can be used in any scala project of version 2.11.+ and 2.12.+ 

## Table of contents

1. [Quick start](#quick-start)
2. [Usage](#usage)
3. [License](#license)

## Quick start

To use **scala-sample** add following to build file:

```scala
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "com.github.nikdon" % "scala-sample" % "1.0.0"
```

## Usage

```scala
import com.github.nikdon.sample.Sample

import java.time.Instant

sealed trait Foo
case class Fiz(fiz: String) extends Foo
case class Buz(buz: String) extends Foo

case class SomeEvent(happenedAt: Instant)


object Test extends App {
  val foo = Sample[Foo].get  // returns an instance of type Foo (Fiz or Buz)

  val nFoo = Sample[Foo].getN(2) // returns a List[Foo] of size 2
  
  // in some cases it is necessary to provide an implementation of `Sample` 
  implicit val instantSample = new Sample[Instant] {
    override def get: Instant = Instant.now
  }
  
  val event = Sample[SomeEvent].get // returns a SomeEvent
  
  // it works with Option[_] and Either[_, _]
  val someEvent = Sample[Option[SomeEvent]].get
  val eitherStringOrEvent = Sample[Either[String, SomeEvent]].get
   
  println(s"foo                 = $foo")
  println(s"nFoo                = $nFoo")
  println(s"event               = $event")    
  println(s"someEvent           = $someEvent")    
  println(s"eitherStringOrEvent = $eitherStringOrEvent")    
}
```

## License

Please refer to [**shapeless guide**](https://github.com/underscoreio/shapeless-guide)'s license.
