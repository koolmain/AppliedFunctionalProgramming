package com.bpb.afp.chap03

@main def lambdaExample() =
  // A simple lambda function that adds 1 to an integer
  val addOne = (x: Int) => x + 1
  println(addOne(5))  // Output: 6