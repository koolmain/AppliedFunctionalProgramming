package com.bpb.afp.chap05

import zio.* 

object ZioConditionalFlow extends ZIOAppDefault {

  // Simulate checking user access (pretend it's an API call)
  def checkAccess(user: String): UIO[Boolean] = ZIO.succeed(user == "admin")

  // Task when access is granted
  val accessGranted: UIO[Unit] = ZIO.succeed(println("Access granted!"))

  // Task when access is denied
  val accessDenied: UIO[Unit] = ZIO.succeed(println("Access denied!"))

  val program: ZIO[Any, Nothing, Unit] = for {
    user <- ZIO.succeed("admin") // Change to "guest" to see different behavior
    _    <- ZIO.ifZIO(checkAccess(user))(accessGranted, accessDenied)
  } yield ()

  override def run: ZIO[Any, Nothing, Unit] = program
}