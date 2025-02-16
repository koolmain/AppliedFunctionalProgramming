package com.bpb.afp.chap03

import scala.util.{Try, Success, Failure}

@main def tryExample() =
  // A function that can throw an exception
  def divide(x: Int, y: Int): Int = x / y

  // Wrapping the division in Try to catch potential exceptions
  val result1 = Try(divide(10, 2))  // This should succeed
  val result2 = Try(divide(10, 0))  // This will fail due to division by zero

  result1 match
    case Success(value) => println(s"Division result: $value")
    case Failure(exception) => println(s"Error occurred: ${exception.getMessage}")

  result2 match {
    case Success(value) => println(s"Division result: $value")
    case Failure(exception) => println(s"Error occurred: ${exception.getMessage}")
  }