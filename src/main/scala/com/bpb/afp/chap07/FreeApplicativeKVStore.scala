package com.bpb.afp.chap07

// import cats.~>
// import cats.data.Kleisli
// import cats.effect.{IO, IOApp}
// import cats.free.FreeApplicative

// // Define the algebra (DSL) for the key-value store
// enum KVStore[A] {
//   case Get(key: String) extends KVStore[Option[String]]
//   case Put(key: String, value: String) extends KVStore[Unit]
// }

// // Free Applicative DSL
// type KVStoreFA[A] = FreeApplicative[KVStore, A]

// // Smart constructors for the DSL operations
// def get(key: String): KVStoreFA[Option[String]] =
//   FreeApplicative.lift(KVStore.Get(key))

// def put(key: String, value: String): KVStoreFA[Unit] =
//   FreeApplicative.lift(KVStore.Put(key, value))

// // Interpreter for the Free Applicative into IO
// val kvStoreInterpreter: KVStore ~> IO = new (KVStore ~> IO) {
//   private var store: Map[String, String] = Map.empty

//   override def apply[A](fa: KVStore[A]): IO[A] = fa match {
//     case KVStore.Get(key) => IO(store.get(key))
//     case KVStore.Put(key, value) => IO {
//       store = store + (key -> value)
//       ()
//     }
//   }
// }

// // Main application
// object FreeApplicativeCatsEffectExample extends IOApp.Simple {

//   // Build a program using the Free Applicative
//   val program: KVStoreFA[Option[String]] = for {
//     _ <- put("name", "Alice")
//     _ <- put("age", "30")
//     name <- get("name")
//   } yield name

//   // Interpret the Free Applicative program into IO
//   val ioProgram: IO[Option[String]] = program.foldMap(kvStoreInterpreter)

//   // Run the program
//   override def run: IO[Unit] = for {
//     result <- ioProgram
//     _ <- IO.println(s"Result: $result") // Output: Result: Some(Alice)
//   } yield ()
// }