package com.bpb.afp.chap06

import zio.* 
import zio.durationInt

object ZioEffectExample extends ZIOAppDefault:

  val printMessage: ZIO[Any, Nothing, Unit] =
    ZIO.succeed(println("Hello, ZIO!"))

  override def run: ZIO[Any, Nothing, Unit] =
    printMessage


object ZioCompositionExample extends ZIOAppDefault:

  def getUserInput: ZIO[Any, Nothing, String] =
    ZIO.succeed(scala.io.StdIn.readLine())

  def greetUser(name: String): ZIO[Any, Nothing, Unit] =
    ZIO.succeed(println(s"Hello, $name!"))

  override def run: ZIO[Any, Nothing, Unit] = for {
    _    <- ZIO.succeed(println("Enter your name:"))
    name <- getUserInput
    _    <- greetUser(name)
  } yield ()


object ZioErrorHandlingExample extends ZIOAppDefault:

  def divide(x: Int, y: Int): ZIO[Any, String, Int] =
    if (y == 0) ZIO.fail("Cannot divide by zero!")
    else ZIO.succeed(x / y)

  override def run: ZIO[Any, Nothing, Unit] = for {
    _       <- ZIO.succeed(println("Trying to divide 10 by 2..."))
    result1 <- divide(10, 2).catchAll(err => ZIO.succeed(println(s"Error: $err")).as(0))
    _       <- ZIO.succeed(println(s"Result: $result1"))

    _       <- ZIO.succeed(println("Trying to divide 10 by 0..."))
    result2 <- divide(10, 0).catchAll(err => ZIO.succeed(println(s"Error: $err")).as(0))
    _       <- ZIO.succeed(println(s"Recovered Result: $result2"))
  } yield ()


object ZioParallelExample extends ZIOAppDefault:

  def task(name: String): ZIO[Any, Nothing, Unit] =
    ZIO.sleep(2.seconds) *> ZIO.succeed(println(s"Task $name completed!"))

  override def run: ZIO[Any, Nothing, Unit] = for {
    fiber1 <- task("A").fork
    fiber2 <- task("B").fork
    _      <- fiber1.join *> fiber2.join
  } yield ()


object ZioDelayedExample extends ZIOAppDefault {

  def delayedTask: ZIO[Any, Nothing, Unit] =
    ZIO.succeed(println("Starting...")) *> ZIO.sleep(3.seconds) *> ZIO.succeed(println("Finished!"))

  override def run: ZIO[Any, Nothing, Unit] = delayedTask
}