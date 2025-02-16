package com.bpb.afp.chap02

trait ApplicativeFunctor[F[_]]:
  def pure[A](value: A): F[A]
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
  
  // Derived from Functor: Apply a function to a wrapped value
  def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(pure(f))(fa)

given optionApplicative: ApplicativeFunctor[Option] :
  def pure[A](value: A): Option[A] = Some(value)

  def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] =
    (ff, fa) match
      case (Some(f), Some(a)) => Some(f(a))
      case _ => None


def applyExample[F[_]: ApplicativeFunctor, A, B](fa: F[A], f: A => B): F[B] =
  summon[ApplicativeFunctor[F]].map(fa)(f)

@main def runApplicativeFunctorExample() =
  val optFunctor = summon[ApplicativeFunctor[Option]]
  
  val opt1: Option[Int] = Some(10)
  val optFunc: Option[Int => Int] = Some(_ * 2)

  // Applying a function inside an Option
  val result1 = optFunctor.ap(optFunc)(opt1) // Some(20)
  val result2 = applyExample(opt1, _ + 5)    // Some(15)

  println(result1) // Output: Some(20)
  println(result2) // Output: Some(15)
