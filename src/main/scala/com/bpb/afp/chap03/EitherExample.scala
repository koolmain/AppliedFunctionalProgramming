package com.bpb.afp.chap03

def divide(a: Int, b: Int): Either[String, Int] =
  if (b == 0) Left("Cannot divide by zero")
  else Right(a / b)

@main def eitherExample() =
  val result1 = divide(10, 2) // Right(5)
  val result2 = divide(10, 0) // Left("Cannot divide by zero")

  result1 match
    case Right(value) => println(s"Success: $value")
    case Left(error)  => println(s"Error: $error")

  result2 match
    case Right(value) => println(s"Success: $value")
    case Left(error)  => println(s"Error: $error")
