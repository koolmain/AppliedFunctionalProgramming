package com.bpb.afp.chap02

//Foldable definition 
trait Foldable[F[_]]:
  def foldLeft[A, B](fa: F[A], initial: B)(f: (B, A) => B): B

// Instances for List and Option
object Foldable:
  // Foldable instance for List
  given Foldable[List] with
    def foldLeft[A, B](fa: List[A], initial: B)(f: (B, A) => B): B =
      fa.foldLeft(initial)(f)

  // Foldable instance for Option
  given Foldable[Option] with
    def foldLeft[A, B](fa: Option[A], initial: B)(f: (B, A) => B): B =
      fa match
        case Some(a) => f(initial, a)
        case None    => initial

//extension methods for usability 
extension [F[_], A](fa: F[A])(using foldable: Foldable[F])
  def foldLeft[B](initial: B)(f: (B, A) => B): B =
    foldable.foldLeft(fa, initial)(f)

@main def runFoldableExample(): Unit =
  // Example with List
  val numbers = List(1, 2, 3, 4, 5)
  val sum = numbers.foldLeft(0)(_ + _)
  println(s"Sum of numbers: $sum") // Output: Sum of numbers: 15

  // Example with Option
  val maybeNumber: Option[Int] = Some(42)
  val result = maybeNumber.foldLeft(0)(_ + _)
  println(s"Result from Option: $result") // Output: Result from Opti