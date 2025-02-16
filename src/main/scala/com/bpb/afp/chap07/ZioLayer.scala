package com.bpb.afp.chap07

import zio.*

// Logging service definition
trait Logger:
  def log(message: String): UIO[Unit]

// Implementation of Logger
case class ConsoleLogger() extends Logger:
  def log(message: String): UIO[Unit] =
    ZIO.succeed(println(s"[LOG]: $message"))

// Define a ZLayer for dependency injection
object Logger:
  val live: ULayer[Logger] = ZLayer.succeed(ConsoleLogger())

// Business logic that depends on Logger
trait Greeter:
  def greet(name: String): UIO[Unit]

case class GreeterImpl(logger: Logger) extends Greeter:
  def greet(name: String): UIO[Unit] =
    logger.log(s"Hello, $name!")

// Define a ZLayer that requires Logger
object Greeter:
  val live: URLayer[Logger, Greeter] = ZLayer.fromFunction(GreeterImpl.apply)

// Composing multiple layers
val appLayer: ULayer[Greeter] = Logger.live >>> Greeter.live

// Main ZIO program
val program: ZIO[Greeter, Nothing, Unit] =
  for
    greeter <- ZIO.service[Greeter]
    _       <- greeter.greet("Scala Developer")
  yield ()

// Run the program with dependencies injected
@main def runApp: Unit =
    Unsafe.unsafe { implicit unsafe =>
        val result = Runtime.default.unsafe.run(program.provideLayer(appLayer)).getOrThrowFiberFailure()
        println(result) // Output: ("Task 1 completed", "Task 2 completed") 
    }   