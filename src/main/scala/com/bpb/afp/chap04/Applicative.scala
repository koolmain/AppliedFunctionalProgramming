package com.bpb.afp.chap04

import cats.Applicative
import cats.implicits.*

@main def applicativePureExample() =
  val optionApplicative = Applicative[Option]
  val listApplicative = Applicative[List]

  println(optionApplicative.pure(42))   // Some(42)
  println(listApplicative.pure(42))     // List(42)

@main def applicativeApExample() =
  val optionApplicative = Applicative[Option]

  val someFunc: Option[Int => Int] = Some((x: Int) => x * 2)
  val someValue: Option[Int] = Some(10)

  val result = optionApplicative.ap(someFunc)(someValue) // Some(20)
  println(result) 


@main def applicativeMap2Example() =
  val optionApplicative = Applicative[Option]

  val result = optionApplicative.map2(Some(2), Some(3))(_ + _) // Some(5)
  println(result)


case class Box[A](value: A)

object Box:
  implicit val boxApplicative: Applicative[Box] = new Applicative[Box]:
    def pure[A](x: A): Box[A] = Box(x)

    def ap[A, B](ff: Box[A => B])(fa: Box[A]): Box[B] =
      Box(ff.value(fa.value))

@main def customApplicativeExample() = {
  val boxFunc = Box((x: Int) => x * 2)
  val boxValue = Box(10)

  val result = Applicative[Box].ap(boxFunc)(boxValue)  // Box(20)
  println(result)
}