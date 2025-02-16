package com.bpb.afp.chap02

trait Semigroup[A]:
  def combine(x: A, y: A): A

object IntAdditionSemigroup extends Semigroup[Int]:
  def combine(x: Int, y: Int): Int = x + y

object StringConcatenationSemigroup extends Semigroup[String]:
  def combine(x: String, y: String): String = x + y

object SemigroupExample:
  def combineValues[A](a: A, b: A)(implicit semigroup: Semigroup[A]): A =
    semigroup.combine(a, b)

  def main(args: Array[String]): Unit =
    // Manually passing instances
    println(IntAdditionSemigroup.combine(10, 20))  // Output: 30
    println(StringConcatenationSemigroup.combine("Hello, ", "Scala!"))  // Output: Hello, Scala!

    // Using implicit resolution
    implicit val intSemigroup: Semigroup[Int] = IntAdditionSemigroup
    implicit val stringSemigroup: Semigroup[String] = StringConcatenationSemigroup

    println(combineValues(5, 10))  // Output: 15
    println(combineValues("Functional ", "Programming"))  // Output: Functional Programming


case class Box(value: Int)

implicit object BoxSemigroup extends Semigroup[Box]:
  def combine(a: Box, b: Box): Box = Box(a.value + b.value)

object CustomSemigroupExample extends App {
  def combineValues[A](a: A, b: A)(implicit semigroup: Semigroup[A]): A =
    semigroup.combine(a, b)

  val box1 = Box(10)
  val box2 = Box(20)

  println(combineValues(box1, box2))  // Output: Box(30)
}