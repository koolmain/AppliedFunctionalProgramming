package com.bpb.afp.chap06

import cats.effect.* 
import scala.concurrent.duration.*
import cats.effect.std.Queue

object HelloWorld extends IOApp.Simple:

  def greet(name: String): IO[Unit] =
    IO.println(s"Hello, $name!")

  override def run: IO[Unit] =
    greet("Applied functional programming reader")


object IOChaining extends IOApp.Simple:

  def getUserInput: IO[String] =
    IO.readLine

  def greetUser(name: String): IO[Unit] =
    IO.println(s"Hello, $name!")

  override def run: IO[Unit] =
    IO.println("Enter your name:") *> // Sequence execution
    getUserInput.flatMap(greetUser)


object IOErrorHandling extends IOApp.Simple:

  def riskyOperation: IO[Unit] =
    IO.raiseError(new Exception("Something went wrong!"))

  def safeOperation: IO[Unit] =
    riskyOperation.handleErrorWith { e =>
      IO.println(s"Caught error: ${e.getMessage}")
    }

  override def run: IO[Unit] = safeOperation



object ResourceExample extends IOApp.Simple:

  def openFile(name: String): Resource[IO, Unit] =
    Resource.make(IO.println(s"Opening $name"))(_ => IO.println(s"Closing $name"))

  override def run: IO[Unit] =
    openFile("file.txt").use(_ => IO.println("Reading file..."))

object QueueExample extends IOApp.Simple:

  override def run: IO[Unit] = for {
    queue <- Queue.unbounded[IO, String]
    _     <- queue.offer("Hello, Cats Effect!").start
    msg   <- queue.take
    _     <- IO.println(s"Received message: $msg")
  } yield ()


object RefExample extends IOApp.Simple {

  override def run: IO[Unit] = for {
    counter <- Ref[IO].of(0) // Create atomic reference
    _       <- counter.update(_ + 1).start
    _       <- counter.update(_ + 1).start
    value   <- counter.get
    _       <- IO.println(s"Final count: $value")
  } yield ()
}