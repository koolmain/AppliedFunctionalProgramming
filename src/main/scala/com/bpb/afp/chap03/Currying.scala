package com.bpb.afp.chap03

def multiply(x: Int)(y: Int)(z: Int): Int = x * y * z

@main def curryingWithMoreArgs() =
  val multiplyBy2 = multiply(2)  // Returns a function taking two more arguments
  val multiplyBy2And3 = multiplyBy2(3)  // Now returns a function that multiplies by 6
  println(multiplyBy2And3(4))  // Output: 24 (2 * 3 * 4)
