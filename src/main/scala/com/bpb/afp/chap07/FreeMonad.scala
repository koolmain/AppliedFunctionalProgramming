package com.bpb.afp.chap07

// import cats.free.Free

// // Algebra (DSL) for a Key-Value Store
// sealed trait KVStoreOp[A]
// case class Put(key: String, value: String) extends KVStoreOp[Unit]
// case class Get(key: String) extends KVStoreOp[Option[String]]
// case class Delete(key: String) extends KVStoreOp[Unit]


// // Define Free Monad for KVStore
// type KVStore[A] = Free[KVStoreOp, A]

// // Smart constructors to lift operations into Free Monad
// object KVStore {
//   def put(key: String, value: String): KVStore[Unit] =
//     Free.liftF(Put(key, value))

//   def get(key: String): KVStore[Option[String]] =
//     Free.liftF(Get(key))

//   def delete(key: String): KVStore[Unit] =
//     Free.liftF(Delete(key))
// }

// import KVStore._

// val program: KVStore[Option[String]] =
//   for {
//     _    <- put("foo", "bar")   // Put key "foo" with value "bar"
//     _    <- put("baz", "qux")   // Put another key-value
//     _    <- delete("baz")       // Delete key "baz"
//     res  <- get("foo")          // Get value of "foo"
//   } yield res


// import cats.effect.IO
// import cats.~>

// // Interpreter: Executes KVStore operations using IO
// val interpreter: KVStoreOp ~> IO = new (KVStoreOp ~> IO) {
//   private var store: Map[String, String] = Map()

//   def apply[A](fa: KVStoreOp[A]): IO[A] = fa match {
//     case Put(key, value) => IO { store += (key -> value) }
//     case Get(key)        => IO.pure(store.get(key))
//     case Delete(key)     => IO { store -= key }
//   }
// }


// import cats.effect.unsafe.implicits.global

// // Run the program using the interpreter
// val result: IO[Option[String]] = program.foldMap(interpreter)

// // Execute the IO effect
// result.map(res => println(s"Result: $res")).unsafeRunSync()