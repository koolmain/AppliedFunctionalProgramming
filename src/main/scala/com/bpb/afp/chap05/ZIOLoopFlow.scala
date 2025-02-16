package com.bpb.afp.chap05

import zio.* 

object RecursiveLoop extends ZIOAppDefault:

  def countdown(n: Int): UIO[Unit] =
    if (n <= 0) ZIO.succeed(println("Done!"))
    else ZIO.succeed(println(s"Countdown: $n")) *> countdown(n - 1)

  override def run: ZIO[Any, Nothing, Unit] = countdown(5)


object RepeatUntilExample extends ZIOAppDefault:

  def fetchRandomNumber: UIO[Int] = 
    ZIO.succeed(scala.util.Random.nextInt(10)) // Random number between 0-9

  val program: ZIO[Any, Nothing, Int] = 
    fetchRandomNumber.repeatUntil(_ > 5) // Keep retrying until number > 5

  override def run: ZIO[Any, Nothing, Unit] =
    program.flatMap(n => ZIO.succeed(println(s"Found: $n")))

object RepeatWhileExample extends ZIOAppDefault:

  def printCounter(n: Int): UIO[Int] = 
    ZIO.succeed(println(s"Counter: $n")).as(n + 1)

  val program: ZIO[Any, Nothing, Int] = 
    ZIO.succeed(0).flatMap(printCounter).repeatWhile(_ < 5)

  override def run: ZIO[Any, Nothing, Unit] =
    program.flatMap(n => ZIO.succeed(println(s"Loop exited at: $n")))

