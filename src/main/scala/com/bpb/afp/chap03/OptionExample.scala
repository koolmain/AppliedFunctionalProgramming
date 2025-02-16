package com.bpb.afp.chap03

val someValue: Option[Int] = Some(10)
val noValue: Option[Int] = None

@main def getOrElseExample() =
  println(someValue.getOrElse(0))  // Output: 10
  println(noValue.getOrElse(0))    // Output: 0
