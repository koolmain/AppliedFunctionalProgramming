package com.bpb.afp.chap10

import cats.effect._
import doobie._
import doobie.hikari._
import doobie.implicits._

object Database {
  def transactorResource: Resource[IO, HikariTransactor[IO]] = 
    HikariTransactor.newHikariTransactor[IO](
      driverClassName = "org.postgresql.Driver", 
      url = "jdbc:postgresql://localhost:5432/mydb",
      user = "myuser",
      pass = "mypassword"
    )
}

object SimpleQuery extends IOApp.Simple {
  def currentTimeQuery: ConnectionIO[String] =
    sql"SELECT NOW()".query[String].unique

  def program: IO[Unit] = Database.transactorResource.use { xa =>
    for {
      time <- currentTimeQuery.transact(xa)
      _ <- IO.println(s"Current time from DB: $time")
    } yield ()
  }

  override def run: IO[Unit] = program
}

def createTable: ConnectionIO[Int] =
  sql"""
    CREATE TABLE IF NOT EXISTS users (
      id SERIAL PRIMARY KEY,
      name TEXT NOT NULL,
      age INT NOT NULL
    )
  """.update.run

def insertUser(name: String, age: Int): ConnectionIO[Int] =
  sql"INSERT INTO users (name, age) VALUES ($name, $age)".update.run

def getUsers: ConnectionIO[List[(Int, String, Int)]] =
  sql"SELECT id, name, age FROM users".query[(Int, String, Int)].to[List]

object DoobieExample extends IOApp.Simple {
  def createTable: ConnectionIO[Int] =
    sql"""
      CREATE TABLE IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        name TEXT NOT NULL,
        age INT NOT NULL
      )
    """.update.run

  def insertUser(name: String, age: Int): ConnectionIO[Int] =
    sql"INSERT INTO users (name, age) VALUES ($name, $age)".update.run

  def getUsers: ConnectionIO[List[(Int, String, Int)]] =
    sql"SELECT id, name, age FROM users".query[(Int, String, Int)].to[List]

  def program: IO[Unit] = Database.transactorResource.use { xa =>
    for {
      _ <- createTable.transact(xa)
      _ <- insertUser("Alice", 25).transact(xa)
      _ <- insertUser("Bob", 30).transact(xa)
      users <- getUsers.transact(xa)
      _ <- IO.println(s"Users in DB: $users")
    } yield ()
  }

  override def run: IO[Unit] = program
}

case class User(id: Int, name: String, age: Int)

def getUsers: ConnectionIO[List[User]] =
  sql"SELECT id, name, age FROM users".query[User].to[List]

  def transactionalInsert(name1: String, age1: Int, name2: String, age2: Int): ConnectionIO[Unit] =
  for {
    _ <- insertUser(name1, age1)
    _ <- insertUser(name2, age2)
  } yield ()

  transactionalInsert("Alice", 25, "Bob", 30).transact(xa)
