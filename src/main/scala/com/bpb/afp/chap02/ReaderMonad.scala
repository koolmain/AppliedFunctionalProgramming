package com.bpb.afp.chap02

trait Reader[R, A]:
  def run(env: R): A

  // Functor: Apply a function to the result
  def map[B](f: A => B): Reader[R, B] =
    Reader(env => f(run(env)))

  // Monad: Chain computations that depend on the environment
  def flatMap[B](f: A => Reader[R, B]): Reader[R, B] =
    Reader(env => f(run(env)).run(env))

object Reader:
  def apply[R, A](run1: R => A): Reader[R, A] = new Reader[R, A]:
    def run(env: R): A = run1(env)

// trait Monad[F[_]] {
//   def pure[A](value: A): F[A]
//   def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  
//   // Derived method
//   def map[A, B](fa: F[A])(f: A => B): F[B] =
//     flatMap(fa)(a => pure(f(a)))
// }

// Monad instance for Reader
given readerMonad[R]: Monad[[A] =>> Reader[R, A]] with
  def pure[A](value: A): Reader[R, A] = Reader(_ => value)

  def flatMap[A, B](fa: Reader[R, A])(f: A => Reader[R, B]): Reader[R, B] =
    fa.flatMap(f)

case class Config(dbUrl: String, apiKey: String)

def getDatabaseUrl: Reader[Config, String] =
  Reader(config => config.dbUrl)

def getApiKey: Reader[Config, String] =
  Reader(config => config.apiKey)

def fullConfigInfo: Reader[Config, String] =
  for {
    dbUrl <- getDatabaseUrl
    apiKey <- getApiKey
  } yield s"DB: $dbUrl, API Key: $apiKey"


@main def runReaderExample() =
  val config = Config("jdbc:mysql://localhost:3306/mydb", "secret-api-key")

  val result = fullConfigInfo.run(config)
  println(result) // Output: "DB: jdbc:mysql://localhost:3306/mydb, API Key: secret-api-key"
