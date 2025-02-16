package com.bpb.afp.chap05

import zio.* 

object ZioFoldExample extends ZIOAppDefault {

  def divide(a: Int, b: Int): IO[String, Int] =
    if (b == 0) ZIO.fail("Cannot divide by zero")
    else ZIO.succeed(a / b)

  val program: UIO[Int] = divide(10, 0).foldZIO(
    err => ZIO.succeed(println(s"Error: $err")).as(-1),  // On failure
    res => ZIO.succeed(println(s"Result: $res")).as(1)         // On success
  )

  override def run: ZIO[Any, Nothing, Unit] = program.flatMap(n => ZIO.succeed(println(s"Found: $n")))
}