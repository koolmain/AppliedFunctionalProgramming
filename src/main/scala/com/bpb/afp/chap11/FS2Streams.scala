package com.bpb.afp.chap11

import cats.effect.{IO, IOApp}
import cats.implicits.*
import fs2.{Stream,text}
import scala.concurrent.duration.*
import java.nio.file.Paths

object SimpleFS2Example extends IOApp.Simple:
  val stream: Stream[IO, Int] = Stream.emits(List(1, 2, 3, 4, 5))

  val program: IO[Unit] = stream.evalMap(n => IO.println(n)).compile.drain

  override def run: IO[Unit] = program


object InfiniteFS2Stream extends IOApp.Simple:
  val infiniteStream: Stream[IO, Int] = Stream.iterate(0)(_ + 1)

  val program: IO[Unit] = infiniteStream
    .metered(1.second) // Emits one element per second
    .evalMap(n => IO.println(s"Tick: $n"))
    .take(5) // Limit to 5 elements
    .compile
    .drain

  override def run: IO[Unit] = program

object FS2StreamMergeExample extends IOApp.Simple:
  val streamA = Stream.iterate(1)(_ + 1)
  val streamB = Stream.awakeEvery[IO](500.millis).map(_.toMillis)

  val mergedStream = streamA.zip(streamB)

  val program: IO[Unit] = mergedStream
    .evalMap { case (a, b) => IO.println(s"StreamA: $a, StreamB: $b") }
    .take(5)
    .compile
    .drain

  override def run: IO[Unit] = program
object FS2ResourceExample extends IOApp.Simple:
  def acquire: IO[Unit] = IO.println("Acquiring resource...")
  def release: IO[Unit] = IO.println("Releasing resource...")

  val resourceStream: Stream[IO, Unit] =
    Stream.bracket(acquire)(_ => release)

  val program: IO[Unit] = resourceStream.compile.drain

  override def run: IO[Unit] = program


object FS2ErrorHandlingExample extends IOApp.Simple:
  val failingStream: Stream[IO, Int] = 
    Stream(1, 2, 3) ++ Stream.raiseError[IO](new RuntimeException("Boom!"))

  val handledStream: Stream[IO, Int] = failingStream.handleErrorWith { _ =>
    Stream(100, 200)
  }

  val program: IO[Unit] = handledStream.evalMap(n => IO.println(n)).compile.drain

  override def run: IO[Unit] = program

object FS2FileWriteExample extends IOApp.Simple {
  val stream: Stream[IO, String] = Stream("Hello", "FS2!", "Streaming is fun!")

  val program: IO[Unit] = stream
    .intersperse("\n")
    .through(text.utf8.encode)
    .through(fs2.io.file.Files[IO].writeAll(Paths.get("output.txt")))
    .compile
    .drain

  override def run: IO[Unit] = program
}