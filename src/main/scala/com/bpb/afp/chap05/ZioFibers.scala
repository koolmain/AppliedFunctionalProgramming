package com.bpb.afp.chap05

import zio.* 

object ZioFiberExample extends ZIOAppDefault:

  def printMessage(message: String): UIO[Unit] =
    ZIO.succeed(println(message))

  val program: ZIO[Any, Nothing, Unit] = for {
    fiber <- printMessage("Hello from Fiber!").fork  // Start in a new fiber
    _     <- fiber.join                              // Wait for fiber to complete
  } yield ()

  override def run: ZIO[Any, Nothing, Unit] = program


object ParallelFibers extends ZIOAppDefault:

  val task1 = ZIO.succeed(println("Task 1 running")).delay(1.second)
  val task2 = ZIO.succeed(println("Task 2 running")).delay(1.second)

  val program: ZIO[Any, Nothing, Unit] = for {
    fiber1 <- task1.fork
    fiber2 <- task2.fork
    _      <- fiber1.join *> fiber2.join // Wait for both fibers
  } yield ()

  override def run: ZIO[Any, Nothing, Unit] = program


object FiberRaceExample extends ZIOAppDefault:

  val fastTask  = ZIO.succeed(println("Fast task wins!")).delay(1.second)
  val slowTask  = ZIO.succeed(println("Slow task wins!")).delay(3.seconds)

  val program: ZIO[Any, Nothing, Unit] = fastTask.race(slowTask)

  override def run: ZIO[Any, Nothing, Unit] = program


object InterruptFiber extends ZIOAppDefault:

  val longRunningTask: UIO[Unit] = 
    ZIO.succeed(println("Running...")).delay(5.seconds)

  val program: ZIO[Any, Nothing, Unit] = for {
    fiber <- longRunningTask.fork
    _     <- ZIO.sleep(2.seconds) *> fiber.interrupt // Interrupt after 2 seconds
    _     <- ZIO.succeed(println("Fiber interrupted!"))
  } yield ()

  override def run: ZIO[Any, Nothing, Unit] = program


object ZipParExample extends ZIOAppDefault {

  val task1 = ZIO.succeed(10).delay(2.seconds)
  val task2 = ZIO.succeed(20).delay(2.seconds)

  val program: ZIO[Any, Nothing, Int] = 
    task1.zipPar(task2).map { case (a, b) => a + b }

  override def run: ZIO[Any, Nothing, Unit] =
    program.flatMap(result => ZIO.succeed(println(s"Sum: $result")))
}