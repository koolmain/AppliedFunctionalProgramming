package  com.bpb.afp.chap04

import cats.*
import cats.implicits.*
import cats.data.Validated

@main def catsAlternativeExample() =
  // `Option` is an instance of `Alternative`, meaning we can use `empty` and `combineK`
  val someValue: Option[Int] = Some(10)
  val noValue: Option[Int] = None

  // `empty` for `Option` is `None`
  println(s"Empty Option: ${Option.empty[Int]}")  // Output: None

  // `combineK` will combine two `Option` values
  // It returns the first non-empty `Option`
  val combined = someValue.combineK(noValue)  // Some(10)
  println(s"Combined Option: $combined")  // Output: Some(10)

  val combined2 = noValue.combineK(Some(20))  // Some(20)
  println(s"Combined Option (with None first): $combined2")  // Output: Some(20)


@main def listAlternativeExample() =
  // `List.empty` is the "empty" value for `List`
  val list1 = List(1, 2, 3)
  val list2 = List(4, 5)

  // `combineK` will concatenate the lists
  val combinedList = list1.combineK(list2)  // List(1, 2, 3, 4, 5)
  println(s"Combined List: $combinedList")  // Output: List(1, 2, 3, 4, 5)



@main def eitherAlternativeExample() =
  // `Either.empty` is `Left` with a default value
  val either1: Either[String, Int] = Right(10)
  val either2: Either[String, Int] = Left("Error")

  // `combineK` for `Either` prefers the first non-`Left` value
  val combinedEither = either1.combineK(either2)  // Right(10)
  println(s"Combined Either: $combinedEither")  // Output: Right(10)


@main def validatedAlternativeExample() = {
  // `Validated.empty` is `Validated.Invalid` with a default error value
  val validResult: Validated[String, Int] = Validated.valid(10)
  val invalidResult: Validated[String, Int] = Validated.invalid("Invalid input")

  // `combineK` accumulates errors in case of invalid results
  val combinedValidated = validResult.combineK(invalidResult)  // Valid(10)
  println(s"Combined Validated (valid first): $combinedValidated")  // Output: Valid(10)

  val combinedValidated2 = invalidResult.combineK(validResult)  // Valid(10)
  println(s"Combined Validated (invalid first): $combinedValidated2")  // Output: Valid(10)
}