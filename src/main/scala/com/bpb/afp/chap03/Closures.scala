package com.bpb.afp.chap03


@main def closureExample() =
  var factor = 2 // External variable
  val multiplier: Int => Int = x => x * factor // Closure captures 'factor'

  println(multiplier(10)) // Output: 20

  factor = 3  // Modifying the captured variable
  println(multiplier(10)) // Output: 30