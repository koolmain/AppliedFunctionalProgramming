package com.bpb.afp.chap10

import slick.jdbc.PostgresProfile.api._

object DatabaseConfig {
  val db = Database.forConfig("mydb")
}



case class User(id: Option[Int], name: String, age: Int)

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def age = column[Int]("age")

  def * = (id.?, name, age) <> (User.tupled, User.unapply)
}

object UsersTable {
  val users = TableQuery[UsersTable]
}


// import DatabaseConfig.db
// import slick.jdbc.PostgresProfile.api._
// import scala.concurrent.ExecutionContext.Implicits.global
// import scala.concurrent.Future

// def createTable(): Future[Unit] = {
//   db.run(UsersTable.users.schema.createIfNotExists)
// }


// def insertUser(user: User): Future[Int] = {
//   db.run(UsersTable.users returning UsersTable.users.map(_.id) += user)
// }

// def getUsers: Future[Seq[User]] = {
//   db.run(UsersTable.users.result)
// }

// def getUsersAboveAge(age: Int): Future[Seq[User]] = {
//   db.run(UsersTable.users.filter(_.age > age).result)
// }

// def updateUser(id: Int, newName: String): Future[Int] = {
//   db.run(UsersTable.users.filter(_.id === id).map(_.name).update(newName))
// }

// def deleteUser(id: Int): Future[Int] = {
//   db.run(UsersTable.users.filter(_.id === id).delete)
// }


import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object SlickExample extends App {
  val user1 = User(None, "Alice", 25)
  val user2 = User(None, "Bob", 30)

  // Create table
  Await.result(createTable(), 5.seconds)

  // Insert users
  val insertedId1 = Await.result(insertUser(user1), 5.seconds)
  val insertedId2 = Await.result(insertUser(user2), 5.seconds)

  println(s"Inserted Users with IDs: $insertedId1, $insertedId2")

  // Fetch all users
  val users = Await.result(getUsers, 5.seconds)
  println(s"Users in DB: $users")

  // Update a user
  Await.result(updateUser(insertedId1, "UpdatedAlice"), 5.seconds)

  // Fetch users above age 25
  val olderUsers = Await.result(getUsersAboveAge(25), 5.seconds)
  println(s"Users above 25: $olderUsers")

  // Delete a user
  Await.result(deleteUser(insertedId1), 5.seconds)

  // Final user list
  val finalUsers = Await.result(getUsers, 5.seconds)
  println(s"Final users in DB: $finalUsers")
}