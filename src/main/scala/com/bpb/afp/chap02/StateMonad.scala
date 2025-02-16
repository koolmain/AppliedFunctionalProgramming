package com.bpb.afp.chap02

trait State[S, A]:
  def run(initial: S): (A, S) // Takes a state and returns (result, new state)

  // Functor: Apply a function to the result
  def map[B](f: A => B): State[S, B] =
    State { s =>
      val (a, newState) = run(s)
      (f(a), newState)
    }

  // Monad: Chain computations that depend on the state
  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State { s =>
      val (a, newState) = run(s)
      f(a).run(newState)
    }

object State:
  def apply[S, A](run1: S => (A, S)): State[S, A] = new State[S, A]:
    def run(initial: S): (A, S) = run1(initial)

  def get[S]: State[S, S] = State(s => (s, s))     // Retrieve the current state
  def set[S](newState: S): State[S, Unit] = State(_ => ((), newState)) // Set a new state
  def modify[S](f: S => S): State[S, Unit] = State(s => ((), f(s))) // Update the state
  def pure[S, A](value: A): State[S, A] = State(s => (value, s)) // Lift a value into State

// trait Monad[F[_]] {
//   def pure[A](value: A): F[A]
//   def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

//   // Derived method
//   def map[A, B](fa: F[A])(f: A => B): F[B] =
//     flatMap(fa)(a => pure(f(a)))
// }

// Monad instance for State
given stateMonad[S]: Monad[[A] =>> State[S, A]] with
  def pure[A](value: A): State[S, A] = State.pure(value)
  def flatMap[A, B](fa: State[S, A])(f: A => State[S, B]): State[S, B] = fa.flatMap(f)

def increment: State[Int, Int] =
  State.modify[Int](_ + 1).flatMap(_ => State.get)

def decrement: State[Int, Int] =
  State.modify[Int](_ - 1).flatMap(_ => State.get)

def double: State[Int, Int] =
  State.modify[Int](_ * 2).flatMap(_ => State.get)

// Composing multiple state updates
def complexComputation: State[Int, Int] =
  for {
    _ <- increment
    _ <- increment
    _ <- double
    result <- decrement
  } yield result

@main def runStateExample() =
  val initialState = 10
  val (result, finalState) = complexComputation.run(initialState)

  println(s"Final Result: $result")   // Output: Final Result: 23
  println(s"Final State: $finalState") // Output: Final State: 23