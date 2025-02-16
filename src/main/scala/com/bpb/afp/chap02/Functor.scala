package com.bpb.afp.chap02

// Step 1: Define the Functor typeclass
trait Functor[F[_]]:
  def map[A, B](fa: F[A])(f: A => B): F[B]

// Step 2: Provide instances for specific types
object Functor:
  // Functor instance for List
  given Functor[List] with
    def map[A, B](fa: List[A])(f: A => B): List[B] =
      fa.map(f)

  // Functor instance for Option
  given Functor[Option] with
    def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      fa.map(f)

  // Functor instance for Either (right-biased)
  given [E]: Functor[[X] =>> Either[E, X]] with
    def map[A, B](fa: Either[E, A])(f: A => B): Either[E, B] =
      fa.map(f)

// Step 3: Add extension methods for usability
extension [F[_], A](fa: F[A])(using functor: Functor[F])
  def map[B](f: A => B): F[B] =
    functor.map(fa)(f)

// Step 4: Use the Functor typeclass
@main def runFunctorExample(): Unit =
  // Example with List
  val numbers = List(1, 2, 3, 4, 5)
  val doubledNumbers = numbers.map(_ * 2)
  println(s"Doubled numbers: $doubledNumbers") // Output: Doubled numbers: List(2, 4, 6, 8, 10)

  // Example with Option
  val maybeNumber: Option[Int] = Some(42)
  val incrementedNumber = maybeNumber.map(_ + 1)
  println(s"Incremented number: $incrementedNumber") // Output: Incremented number: Some(43)

  // Example with Either
  val eitherNumber: Either[String, Int] = Right(42)
  val squaredNumber = eitherNumber.map(x => x * x)
  println(s"Squared number: $squaredNumber") // Output: Squared number: Right(1764)