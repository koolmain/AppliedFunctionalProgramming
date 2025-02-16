package com.bpb.afp.chap11

import cats.effect._
import fs2._
import scala.concurrent.duration._

object Fs2StreamExample extends IOApp.Simple {
  def stream: Stream[IO, Int] = Stream.emits(1 to 10).covary[IO]

  def run: IO[Unit] =
    stream.evalMap(n => IO.println(n)).compile.drain
}

object InfiniteFs2StreamExample extends IOApp.Simple {
  def infiniteStream: Stream[IO, Int] =
    Stream.iterate(1)(_ + 1).metered(1.second) // Emits a number every second

  def run: IO[Unit] =
    infiniteStream.evalMap(n => IO.println(n)).compile.drain
}

object StreamTransformationExample extends IOApp.Simple {
  def numbers: Stream[IO, Int] = Stream.range(1, 20).covary[IO]

  def evenNumbers: Stream[IO, Int] = numbers.filter(_ % 2 == 0)

  def run: IO[Unit] =
    evenNumbers
      .map(n => s"Even number: $n")
      .evalMap(IO.println)
      .compile
      .drain
}


object ParallelProcessingExample extends IOApp.Simple {
  def numbers: Stream[IO, Int] = Stream.range(1, 10).covary[IO]

  def process(n: Int): IO[String] = IO(s"Processing $n").flatTap(IO.println)

  def run: IO[Unit] =
    numbers.parEvalMapUnordered(4)(process).compile.drain
}