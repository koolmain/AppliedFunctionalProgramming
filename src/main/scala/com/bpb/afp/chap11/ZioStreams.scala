package com.bpb.afp.chap11

import zio.* 
import zio.stream.* 
import java.nio.file.Paths

// object SimpleZIOStreamExample extends ZIOAppDefault {
//   val stream: ZStream[Any, Nothing, Int] = ZStream(1, 2, 3, 4, 5)

//   val program: ZIO[Any, Throwable, Unit] = stream
//     .map(n => s"Number: $n")
//     .foreach(Console.printLine)

//   override def run: ZIO[Any, Throwable, Unit] = program
// }


// object InfiniteZIOStream extends ZIOAppDefault {
//   val infiniteStream: ZStream[Any, Nothing, Int] = ZStream.iterate(0)(_ + 1)

//   val program: ZIO[Any, Throwable, Unit] = infiniteStream
//     .schedule(Schedule.spaced(1.second)) // Emit one value per second
//     .take(5) // Limit to 5 elements
//     .foreach(Console.printLine)

//   override def run: ZIO[Any, Throwable, Unit] = program
// }


// object ZIOStreamMergeExample extends ZIOAppDefault {
//   val streamA = ZStream.iterate(1)(_ + 1).schedule(Schedule.spaced(1.second))
//   val streamB = ZStream.iterate(100)(_ + 1).schedule(Schedule.spaced(500.millis))

//   val mergedStream = streamA.zip(streamB)

//   val program: ZIO[Any, Throwable, Unit] = mergedStream
//     .map { case (a, b) => s"StreamA: $a, StreamB: $b" }
//     .take(5)
//     .foreach(Console.printLine)

//   override def run: ZIO[Any, Throwable, Unit] = program
// }

// object ZIOStreamErrorHandling extends ZIOAppDefault {
//   val failingStream: ZStream[Any, String, Int] =
//     ZStream(1, 2, 3) ++ ZStream.fail("Boom!")

//   val handledStream: ZStream[Any, Nothing, Int] = failingStream.catchAll { _ =>
//     ZStream(100, 200)
//   }

//   val program: ZIO[Any, Throwable, Unit] = handledStream.foreach(Console.printLine)

//   override def run: ZIO[Any, Throwable, Unit] = program
// }


// object ZIOStreamResourceExample extends ZIOAppDefault {
//   def acquire: UIO[Unit] = ZIO.succeed(println("Acquiring resource..."))
//   def release: UIO[Unit] = ZIO.succeed(println("Releasing resource..."))

//   val resourceStream: ZStream[Any, Nothing, Unit] =
//     ZStream.acquireRelease(acquire)(_ => release)

//   val program: ZIO[Any, Throwable, Unit] = resourceStream.compile.drain

//   override def run: ZIO[Any, Throwable, Unit] = program
// }

// object ZIOStreamFileWriteExample extends ZIOAppDefault {
//   val stream: ZStream[Any, Nothing, String] = ZStream("Hello", "ZIO!", "Streaming is fun!")

//   val program: ZIO[Any, Throwable, Unit] = stream
//     .intersperse("\n")
//     .mapChunks(chunks => Chunk.fromArray(chunks.mkString.getBytes("UTF-8")))
//     .run(ZSink.fromFile(Paths.get("output.txt")))

//   override def run: ZIO[Any, Throwable, Unit] = program
// }