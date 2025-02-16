package com.bpb.afp.chap07

// import cats.Applicative
// import cats.free.FreeApplicative

// // Define an algebra (DSL) for form validation
// sealed trait ValidationOp[A]

// case class MinLength(value: String, min: Int) extends ValidationOp[Boolean]
// case class MaxLength(value: String, max: Int) extends ValidationOp[Boolean]
// case class Contains(value: String, substr: String) extends ValidationOp[Boolean]


// object ValidationOp {
//   import cats.free.FreeApplicative
  
//   type Validation[A] = FreeApplicative[ValidationOp, A]

//   def minLength(value: String, min: Int): Validation[Boolean] =
//     FreeApplicative.lift(MinLength(value, min))

//   def maxLength(value: String, max: Int): Validation[Boolean] =
//     FreeApplicative.lift(MaxLength(value, max))

//   def contains(value: String, substr: String): Validation[Boolean] =
//     FreeApplicative.lift(Contains(value, substr))
// }

// import ValidationOp.* 

// val validationProgram: ValidationOp.Validation[Boolean] =
//   (minLength("HelloWorld", 5), maxLength("HelloWorld", 20), contains("HelloWorld", "Hello"))
//     .mapN(_ && _ && _)


// import cats.~>

// // Interpreter: Executes the validation program
// val interpreter: ValidationOp ~> Option = new (ValidationOp ~> Option) {
//   def apply[A](fa: ValidationOp[A]): Option[A] = fa match {
//     case MinLength(value, min) => Some(value.length >= min)
//     case MaxLength(value, max) => Some(value.length <= max)
//     case Contains(value, substr) => Some(value.contains(substr))
//   }
// }

// // Run the validation program using our interpreter
// val result: Option[Boolean] = validationProgram.foldMap(interpreter)

// // Print the result
// println(s"Validation Result: $result")  // Output: Some(true)