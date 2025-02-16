package com.bpb.afp.chap06

import cats.effect.* 
import scala.concurrent.duration.*


object IOExample extends IOApp.Simple:

  def printMessage: IO[Unit] =
    IO.println("Hello, Cats Effect!")

  override def run: IO[Unit] =
    printMessage

object ComposeIOExample extends IOApp.Simple:

  def getUserInput: IO[String] =
    IO.readLine

  def greetUser(name: String): IO[Unit] =
    IO.println(s"Hello, $name!")

  override def run: IO[Unit] = for {
    _    <- IO.println("Enter your name:")
    name <- getUserInput
    _    <- greetUser(name)
  } yield ()


object ErrorHandlingExample extends IOApp.Simple:

  def divide(x: Int, y: Int): IO[Int] =
    IO(x / y).handleErrorWith(e => IO.println(s"Error: ${e.getMessage}").as(0))

  override def run: IO[Unit] = for {
    _ <- divide(10, 2).flatMap(result => IO.println(s"Result: $result"))
    _ <- divide(10, 0).flatMap(result => IO.println(s"Recovered Result: $result"))
  } yield ()


object ParallelIOExample extends IOApp.Simple:

  def task(name: String): IO[Unit] =
    IO.sleep(2.seconds) *> IO.println(s"Task $name completed!")

  override def run: IO[Unit] = for {
    fiber1 <- task("A").start
    fiber2 <- task("B").start
    _      <- fiber1.join *> fiber2.join
  } yield ()


object DelayedIOExample extends IOApp.Simple {

  def delayedTask: IO[Unit] =
    IO.println("Starting...") *> IO.sleep(3.seconds) *> IO.println("Finished!")

  override def run: IO[Unit] = delayedTask
}