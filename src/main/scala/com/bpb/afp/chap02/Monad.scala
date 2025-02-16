package com.bpb.afp.chap02

//Monad definition 

trait Monad[F[_]]:
  def pure[A](value: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  // Derived from flatMap
  def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))

given optionMonad: Monad[Option] with
  def pure[A](value: A): Option[A] = Some(value)

  def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
    fa match
      case Some(value) => f(value)
      case None        => None

def processValue[F[_]: Monad, A, B](fa: F[A], f: A => F[B]): F[B] =
  summon[Monad[F]].flatMap(fa)(f)

@main def runMonadExample() =
  val optMonad = summon[Monad[Option]]
  
  val opt1: Option[Int] = Some(10)

  val result1 = optMonad.flatMap(opt1)(x => Some(x * 2)) // Some(20)
  val result2 = processValue(opt1, x => Some(x + 5))     // Some(15)

  println(result1) // Output: Some(20)
  println(result2) // Output: Some(15)