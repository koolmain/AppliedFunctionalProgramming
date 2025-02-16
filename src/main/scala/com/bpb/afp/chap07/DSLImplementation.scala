package com.bpb.afp.chap07

import cats.Applicative
import cats.Id
import cats.effect.IO
import cats.syntax.all.*
import cats.effect.IOApp

// Expression Algebra (DSL)
trait ExprAlg[F[_]]:
  def num(n: Int): F[Int]
  def add(x: F[Int], y: F[Int]): F[Int]
  def mul(x: F[Int], y: F[Int]): F[Int]

// Companion Object for Helper Functions
object ExprAlg:
  def apply[F[_]](using ev: ExprAlg[F]): ExprAlg[F] = ev

// Pure Interpreter (evaluates expressions immediately)
given ExprAlg[Id] with
  def num(n: Int): Id[Int] = n
  def add(x: Id[Int], y: Id[Int]): Id[Int] = x + y
  def mul(x: Id[Int], y: Id[Int]): Id[Int] = x * y

// Effectful Interpreter (evaluates expressions inside IO)
given ExprAlg[IO] with
  def num(n: Int): IO[Int] = IO.pure(n)
  def add(x: IO[Int], y: IO[Int]): IO[Int] = (x, y).mapN(_ + _)
  def mul(x: IO[Int], y: IO[Int]): IO[Int] = (x, y).mapN(_ * _)

// Expression Program (DSL-based computation)
def expr[F[_]: ExprAlg]: F[Int] =
  val alg = ExprAlg[F]
  import alg.*
  
  val x = num(3)
  val y = num(5)
  val sum = add(x, y)
  val product = mul(sum, num(2)) // (3 + 5) * 2
  
  product


@main def runPureEval(): Unit =
  val result: Id[Int] = expr[Id]  // Direct evaluation
  println(s"Pure Evaluation Result: $result")  // Output: 16

object IOExample extends IOApp.Simple {
  override def run: IO[Unit] = 
    expr[IO].flatMap(result => IO.println(s"Effectful Evaluation Result: $result"))  // Output: 16
}