package com.bpb.afp.chap06

import cats.effect.* 
import cats.effect.std.Random
import scala.concurrent.duration.*

object SafeDivide extends IOApp.Simple:

  def safeDivide(x: Int, y: Int): IO[Int] =
    IO(x / y).handleErrorWith(e => IO.println(s"Error: ${e.getMessage}").as(0))

  override def run: IO[Unit] =
    for {
      _ <- safeDivide(10, 2).flatMap(result => IO.println(s"Result: $result"))
      _ <- safeDivide(10, 0).flatMap(result => IO.println(s"Recovered Result: $result"))
    } yield ()


object AttemptExample extends IOApp.Simple:

  def safeDivide(x: Int, y: Int): IO[Int] = IO(x / y)

  override def run: IO[Unit] =
    for {
      result1 <- safeDivide(10, 2).attempt
      result2 <- safeDivide(10, 0).attempt
      _       <- IO.println(s"Success: $result1") // Right(5)
      _       <- IO.println(s"Failure: $result2") // Left(java.lang.ArithmeticException)
    } yield ()


object RedeemExample extends IOApp.Simple:

  def safeDivide(x: Int, y: Int): IO[Int] = IO(x / y)

  override def run: IO[Unit] =
    for {
      result <- safeDivide(10, 0)
        .redeem(_ => "Recovered from error!", res => s"Success: $res")
      _ <- IO.println(result)
    } yield ()
