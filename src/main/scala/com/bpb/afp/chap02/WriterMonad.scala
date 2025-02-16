package com.bpb.afp.chap02

trait Writer[W, A](val run: (A, W)):
  def map[B](f: A => B): Writer[W, B] =
    Writer(f(run._1), run._2)

  def flatMap[B](f: A => Writer[W, B])(using monoid: Monoid[W]): Writer[W, B] =
    val (value, log) = run
    val (newValue, newLog) = f(value).run
    Writer(newValue, monoid.combine(log, newLog))

object Writer:
  def apply[W, A](value: A, log: W): Writer[W, A] = new Writer((value, log)){}
  def tell[W](log: W): Writer[W, Unit] = Writer((), log) // Log something without a value


// trait Monoid[A] {
//   def empty: A
//   def combine(x: A, y: A): A
// }

// given listMonoid[A]: Monoid[List[A]] with {
//   def empty: List[A] = Nil
//   def combine(x: List[A], y: List[A]): List[A] = x ++ y
// }

// Monad instance for Writer
given writerMonad[W: Monoid]: Monad[[A] =>> Writer[W, A]] with
  def pure[A](value: A): Writer[W, A] = Writer(value, summon[Monoid[W]].empty)
  def flatMap[A, B](fa: Writer[W, A])(f: A => Writer[W, B]): Writer[W, B] =
    fa.flatMap(f)

def step1: Writer[List[String], Int] =
  Writer(10, List("Step 1: Start with 10"))

def step2(n: Int): Writer[List[String], Int] =
  Writer(n * 2, List(s"Step 2: Multiply by 2, result = ${n * 2}"))

def step3(n: Int): Writer[List[String], Int] =
  Writer(n - 5, List(s"Step 3: Subtract 5, result = ${n - 5}"))

// Chaining computations with logging
def fullComputation: Writer[List[String], Int] =
  for {
    a <- step1
    b <- step2(a)
    c <- step3(b)
  } yield c

@main def runWriterExample() =
  val (result, logs) = fullComputation.run
  println(s"Final Result: $result") // Output: Final Result: 15
  println("Logs:")
  logs.foreach(println) 