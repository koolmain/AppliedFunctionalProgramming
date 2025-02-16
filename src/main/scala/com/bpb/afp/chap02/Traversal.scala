package com.bpb.afp.chap02

trait Traversal[F[_]]:
  def traverse[A, B](fa: F[A])(f: A => B): F[B]

given listTraversal: Traversal[List] with
  def traverse[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)

given optionTraversal: Traversal[Option] with
  def traverse[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)

def modifyAll[F[_]: Traversal, A, B](fa: F[A], f: A => B): F[B] =
  summon[Traversal[F]].traverse(fa)(f)

@main def runTraversalExample() =
  val numbers = List(1, 2, 3, 4, 5)
  val maybeNumber = Some(10)

  val doubledList = modifyAll(numbers, (x: Int) => x * 2)  // List(2, 4, 6, 8, 10)
  val doubledOption = modifyAll[Option, Int, Int](maybeNumber, (x: Int) => x * 2)  // Some(20)

  println(doubledList)  // Output: List(2, 4, 6, 8, 10)
  println(doubledOption)  // Output: Some(20)
