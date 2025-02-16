package com.bpb.afp.chap05

import zio.* 

object ZioRepeatUntil extends ZIOAppDefault {
  def fetchNumber: UIO[Int] = ZIO.succeed(scala.util.Random.nextInt(10))

  val program: ZIO[Any, Nothing, Int] = fetchNumber.repeatUntil(_ > 5)

  override def run: ZIO[Any, Nothing, Unit] =
    program.flatMap(n => ZIO.succeed(println(s"Found: $n")))
}