package com.bpb.afp.chap04

import cats.Bifunctor

@main def bifunctorEitherExample() =
  val either: Either[String, Int] = Right(42)

  // Use bifunctor to map both Left and Right sides
  val transformedEither = Bifunctor[Either].bimap(either)(
    leftValue  => leftValue.toUpperCase,  // Transform Left
    rightValue => rightValue * 2          // Transform Right
  )

  println(transformedEither) // Output: Right(84)

// Custom ADT with two type parameters
sealed trait MyEither[A, B]
case class MyLeft[A, B](a: A) extends MyEither[A, B]
case class MyRight[A, B](b: B) extends MyEither[A, B]

// Define a Bifunctor instance for MyEither
given myEitherBifunctor: Bifunctor[MyEither] : 
  def bimap[A, B, C, D](fab: MyEither[A, B])(f: A => C, g: B => D): MyEither[C, D] =
    fab match
      case MyLeft(a)  => MyLeft(f(a))  // Transform left value
      case MyRight(b) => MyRight(g(b)) // Transform right value

@main def customBifunctorExample() = {
  val value: MyEither[String, Int] = MyRight(20)

  // Transform both sides
  val transformedValue = Bifunctor[MyEither].bimap(value)(
    left  => left.toUpperCase,  // Transform left
    right => right + 10         // Transform right
  )

  println(transformedValue) // Output: MyRight(30)
}