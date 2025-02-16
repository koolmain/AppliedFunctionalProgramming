package com.bpb.afp.chap05

import zio.* 


// TO DO need to look into 
// object ResourceManagementExample extends ZIOAppDefault {

//   def openFile(path: String): UIO[Unit] =
//     ZIO.succeed(println(s"Opening file: $path"))

//   def closeFile(path: String): UIO[Unit] =
//     ZIO.succeed(println(s"Closing file: $path"))

//   val program: ZIO[Any, Nothing, Unit] = ZIO.acquireRelease(openFile("data.txt"))(_ => closeFile("data.txt"))

//   override def run: ZIO[Any, Nothing, Unit] = program
// }