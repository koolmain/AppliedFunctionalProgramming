package com.bpb.afp.chap05

import zio.* 


// trait GreetingService:
//   def greet(name: String): UIO[String]

// class GreetingServiceImpl extends GreetingService:
//   def greet(name: String): UIO[String] = ZIO.succeed(s"Hello, $name!")

// object GreetingService:
//   val live: ULayer[GreetingService] = ZLayer.succeed(new GreetingServiceImpl)

// object ZLayerBasicExample extends ZIOAppDefault:
//   val program: ZIO[GreetingService, Nothing, Unit] = for {
//     service <- ZIO.service[GreetingService]
//     message <- service.greet("Applied Scala programming reader")
//     _       <- ZIO.succeed(println(message))
//   } yield ()

//   override def run: ZIO[Any, Nothing, Unit] = program.provide(GreetingService.live)


// trait DatabaseService:
//   def fetchUser(id: Int): UIO[String]

// class DatabaseServiceImpl extends DatabaseService:
//   def fetchUser(id: Int): UIO[String] = ZIO.succeed(s"User_$id")

// object DatabaseService:
//   val live: ULayer[DatabaseService] = ZLayer.succeed(new DatabaseServiceImpl)

// trait UserService:
//   def getUserName(id: Int): UIO[String]

// class UserServiceImpl(db: DatabaseService) extends UserService:
//   def getUserName(id: Int): UIO[String] =
//     db.fetchUser(id).map(name => s"Fetched: $name")

// object UserService:
//   val live: ZLayer[DatabaseService, Nothing, UserService] =
//     ZLayer.fromFunction(new UserServiceImpl(_))

// object ZLayerCompositionExample extends ZIOAppDefault:
//   val program: ZIO[UserService, Nothing, Unit] = for {
//     userService <- ZIO.service[UserService]
//     userName    <- userService.getUserName(1)
//     _           <- ZIO.succeed(println(userName))
//   } yield ()

//   override def run: ZIO[Any, Nothing, Unit] =
//     program.provide(UserService.live, DatabaseService.live)


// trait FileService:
//   def readFile(path: String): UIO[String]

// class FileServiceImpl extends FileService:
//   def readFile(path: String): UIO[String] = ZIO.succeed(s"Reading file: $path")

// object FileService:
//   val live: ZLayer[Any, Throwable, FileService] =
//     ZLayer.scoped:
//       ZIO.succeed(println("Opening FileService...")) *> // Setup
//       ZIO.succeed(new FileServiceImpl()).withFinalizer(_ => ZIO.succeed(println("Closing FileService..."))) // Cleanup

// object ResourceManagementExample extends ZIOAppDefault:
//   val program: ZIO[FileService, Nothing, Unit] = for {
//     fileService <- ZIO.service[FileService]
//     content     <- fileService.readFile("file.txt")
//     _           <- ZIO.succeed(println(content))
//   } yield ()

//   override def run: ZIO[Any, Throwable, Unit] =
//     program.provideLayer(FileService.live)
