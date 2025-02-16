package com.bpb.afp.chap04

import cats.Bifoldable
import cats.implicits.* 

@main def bifoldableTupleExample() =
  val tuple: (String, Int) = ("Hello", 100)

  val sum = Bifoldable[Tuple2].bifoldLeft(tuple, 0)((acc, elem) => 
    acc + elem.toString.length
  ,(acc, elem) => 
    acc + elem.toString.length
  )

  println(sum)  // Output: 8 (length of "Hello" + length of "100")


@main def bifoldableBifoldMapExample() = {
  val either: Either[String, Int] = Right(42)

  val result = Bifoldable[Either].bifoldMap(either)(_.length,_ * 2)
  
  println(result)  // Output: 84
}