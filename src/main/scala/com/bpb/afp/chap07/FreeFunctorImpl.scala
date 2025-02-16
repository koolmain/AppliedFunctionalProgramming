package com.bpb.afp.chap07

// import cats.Functor

// // Algebra for Key-Value Store
// sealed trait KVStoreOp[A]
// case class Put(key: String, value: String) extends KVStoreOp[Unit]
// case class Get(key: String) extends KVStoreOp[Option[String]]
// case class Delete(key: String) extends KVStoreOp[Unit]

// import cats.free.FreeT

// // Define a Free Functor for KVStore
// type KVStore[A] = FreeF[KVStoreOp, A]

// // Functor instance for KVStoreOp
// given Functor[KVStoreOp] with {
//   def map[A, B](fa: KVStoreOp[A])(f: A => B): KVStoreOp[B] = fa match {
//     case Put(key, value) => Put(key, value).asInstanceOf[KVStoreOp[B]]
//     case Get(key) => Get(key).asInstanceOf[KVStoreOp[B]]
//     case Delete(key) => Delete(key).asInstanceOf[KVStoreOp[B]]
//   }
// }


// import cats.syntax.functor.* // Enables `map`

// def program: KVStore[Option[String]] = 
//   FreeF.liftF[KVStoreOp, Option[String]](Put("foo", "bar")) *>
//   FreeF.liftF[KVStoreOp, Option[String]](Get("foo"))

// import cats.effect.IO
// import cats.~>

// // Interpreter: Executes the key-value store operations
// val interpreter: KVStoreOp ~> IO = new (KVStoreOp ~> IO) {
//   private var store: Map[String, String] = Map()

//   def apply[A](fa: KVStoreOp[A]): IO[A] = fa match {
//     case Put(key, value) => IO { store += (key -> value) }
//     case Get(key)        => IO.pure(store.get(key))
//     case Delete(key)     => IO { store -= key }
//   }
// }


// import cats.effect.unsafe.implicits.global

// // Run the program with the interpreter
// val result: IO[Option[String]] = program.foldMap(interpreter)

// // Execute the IO effect
// result.map(res => println(s"Result: $res")).unsafeRunSync()
// ✅