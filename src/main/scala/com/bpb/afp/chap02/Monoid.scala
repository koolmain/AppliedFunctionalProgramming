package com.bpb.afp.chap02

//Monoid definition 
trait Monoid[A]:
  def empty: A            // Identity element
  def combine(x: A, y: A): A // Associative binary operation

//Addition Int Monoid instance
given intAdditionMonoid: Monoid[Int] with
  def empty: Int = 0
  def combine(x: Int, y: Int): Int = x + y

//Multiplication Int Monoid instance
given intMultiplicationMonoid: Monoid[Int] with
  def empty: Int = 1
  def combine(x: Int, y: Int): Int = x * y

// String monoid instance
given stringMonoid: Monoid[String] with
  def empty: String = ""
  def combine(x: String, y: String): String = x + y

given listMonoid[A]: Monoid[List[A]] with
  def empty: List[A] = Nil
  def combine(x: List[A], y: List[A]): List[A] = x ++ y

def combineAll[A](values: List[A])(using monoid: Monoid[A]): A =
  values.foldLeft(monoid.empty)(monoid.combine)

@main def runMonoidExample() =
  val numbers = List(1, 2, 3, 4, 5)
  val words = List("Hello", " ", "World", "!")

  val sumResult = combineAll(numbers)(using intAdditionMonoid)   // Uses intAdditionMonoid: Result = 15
  val productResult = combineAll(numbers)(using intMultiplicationMonoid) // Result = 120
  val concatResult = combineAll(words)  // Uses stringMonoid: Result = "Hello World!"

  println(sumResult)       // Output: 15
  println(productResult)   // Output: 120
  println(concatResult)    // Output: "Hello World!"