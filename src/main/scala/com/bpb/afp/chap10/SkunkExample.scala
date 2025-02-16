package com.bpb.afp.chap10

import cats.effect._
import skunk._
import skunk.implicits._
import skunk.codec.all._
import natchez.Trace.Implicits.noop

object DatabaseSkunk {
  def sessionResource: Resource[IO, Session[IO]] = 
    Session.single(
      host = "localhost",
      port = 5432,
      user = "myuser",
      database = "mydb",
      password = Some("mypassword")
    )
}

case class User(id: Int, name: String, age: Int)


def insertUser(name: String, age: Int): Command[User] =
  sql"INSERT INTO users (name, age) VALUES ($text, $int4)".command
    .gcontramap[User]

def insertUserQuery(user: User): IO[Unit] = DatabaseSkunk.sessionResource.use { session =>
  session.prepare(insertUser(user.name, user.age)).use { cmd =>
    cmd.execute(user).void
  }
}

def getUsers: Query[Void, User] =
  sql"SELECT id, name, age FROM users".query(int4 *: text *: int4).map {
    case id *: name *: age => User(id, name, age)
  }

def fetchUsers: IO[List[User]] = DatabaseSkunk.sessionResource.use { session =>
  session.execute(getUsers)
}

def getUsersAboveAge(age: Int): Query[Int, User] =
  sql"SELECT id, name, age FROM users WHERE age > $int4".query(int4 *: text *: int4).map {
    case id *: name *: age => User(id, name, age)
  }

def fetchOlderUsers(age: Int): IO[List[User]] = DatabaseSkunk.sessionResource.use { session =>
  session.prepare(getUsersAboveAge(age)).use { q =>
    q.stream(age, 10).compile.toList
  }
}

def updateUserName(id: Int, newName: String): Command[(String, Int)] =
  sql"UPDATE users SET name = $text WHERE id = $int4".command


def updateUser(id: Int, name: String): IO[Unit] = DatabaseSkunk.sessionResource.use { session =>
  session.prepare(updateUserName(id, name)).use { cmd =>
    cmd.execute(name -> id).void
  }
}

def deleteUser(id: Int): Command[Int] =
  sql"DELETE FROM users WHERE id = $int4".command

def deleteUserQuery(id: Int): IO[Unit] = DatabaseSkunk.sessionResource.use { session =>
  session.prepare(deleteUser(id)).use { cmd =>
    cmd.execute(id).void
  }
}

import cats.effect._
import skunk._
import skunk.implicits._
import skunk.codec.all._
import natchez.Trace.Implicits.noop

object SkunkExample extends IOApp.Simple {
  val user1 = User(0, "Alice", 25)
  val user2 = User(0, "Bob", 30)

  def program: IO[Unit] = DatabaseSkunk.sessionResource.use { session =>
    for {
      // Insert users
      _ <- insertUserQuery(user1)
      _ <- insertUserQuery(user2)

      // Fetch all users
      users <- fetchUsers
      _ <- IO.println(s"Users in DB: $users")

      // Update a user
      _ <- updateUser(1, "UpdatedAlice")

      // Fetch users above 25
      olderUsers <- fetchOlderUsers(25)
      _ <- IO.println(s"Users above 25: $olderUsers")

      // Delete a user
      _ <- deleteUserQuery(1)

      finalUsers <- fetchUsers
      _ <- IO.println(s"Final users in DB: $finalUsers")
    } yield ()
  }

  override def run: IO[Unit] = program
}