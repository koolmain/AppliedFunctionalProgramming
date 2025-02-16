package com.bpb.afp.chap03

// A function that takes an integer and a function, and applies the function to the integer
def applyFunctionToInt(f: Int => Int, x: Int): Int = f(x)

@main def functionAsArgumentExample() =
  val increment = (x: Int) => x + 1  // A function that adds 1
  val double = (x: Int) => x * 2     // A function that multiplies by 2

  println(applyFunctionToInt(increment, 5)) // Output: 6
  println(applyFunctionToInt(double, 5))    // Output: 10
