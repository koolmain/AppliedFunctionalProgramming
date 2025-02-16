package com.bpb.afp.chap05

import zio.* 


object RefExample extends ZIOAppDefault:

  def increment(ref: Ref[Int]): UIO[Int] =
    ref.updateAndGet(_ + 1)

  override def run: ZIO[Any, Nothing, Unit] = for {
    ref <- Ref.make(0)
    _   <- ZIO.collectAllPar(List.fill(5)(increment(ref))) // 5 parallel updates
    result <- ref.get
    _   <- ZIO.succeed(println(s"Final count: $result"))
  } yield ()

object PromiseExample extends ZIOAppDefault:

 def worker(p: Promise[Nothing, String]): UIO[Boolean] =
    ZIO.succeed(println("Processing...")) *> 
    ZIO.sleep(2.seconds) *> 
    p.succeed("Work Done!")

  override def run: ZIO[Any, Nothing, Unit] = for {
    promise <- Promise.make[Nothing, String]
    _ <- worker(promise).fork // Start worker fiber
    result <- promise.await // Wait for result
    _ <- ZIO.succeed(println(s"Worker Result: $result"))
  } yield ()

object SemaphoreExample extends ZIOAppDefault {

  def task(id: Int, semaphore: Semaphore): UIO[Unit] =
    semaphore.withPermit (
      ZIO.succeed(println(s"Task $id is running")) *> ZIO.sleep(1.second))

  override def run: ZIO[Any, Nothing, Unit] = for {
    semaphore <- Semaphore.make(2) // Max 2 concurrent tasks
    _ <- ZIO.collectAllPar(List.tabulate(5)(i => task(i, semaphore)))
  } yield ()
}