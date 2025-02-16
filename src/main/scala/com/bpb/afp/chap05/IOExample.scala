package com.bpb.afp.chap05

import zio.*

@main def zioErrorHandlingExample(): Unit =
  val divide: ZIO[Any, String, Int] = 
    ZIO.succeed(10).flatMap { numerator =>
      ZIO.succeed(0).flatMap { denominator =>
        if (denominator == 0) ZIO.fail("Cannot divide by zero!")
        else ZIO.succeed(numerator / denominator)
      }
    }

  val handled = divide.catchAll(error => ZIO.succeed(s"Error: $error"))

  val runtime = Runtime.default

    Unsafe.unsafe { implicit unsafe =>
    val result = runtime.unsafe.run(handled).getOrThrowFiberFailure()
    println(result) // Output: ("Cannot divide by zero")
  }