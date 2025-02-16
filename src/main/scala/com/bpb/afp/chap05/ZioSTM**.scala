 package com.bpb.afp.chap05

// import zio.* 

// import zio.{ZIO, ZIOAppDefault, Console}
// import zio.stm.{STM, TRef}

// // A simple bank account model using STM
// case class BankAccount(balance: TRef[Int]) {

//   // Deposit money into the account
//   def deposit(amount: Int): STM[String, Unit] =
//     if (amount <= 0) STM.fail("Deposit amount must be positive")
//     else balance.update(_ + amount)

//   // Withdraw money from the account
//   def withdraw(amount: Int): STM[String, Unit] =
//     if (amount <= 0) STM.fail("Withdrawal amount must be positive")
//     else
//       balance.get.flatMap { currentBalance =>
//         if (currentBalance < amount) STM.fail("Insufficient funds")
//         else balance.update(_ - amount)
//       }

//   // Check the current balance
//   def checkBalance: STM[Nothing, Int] = balance.get
// }

// object BankAccount {
//   // Create a new bank account with an initial balance
//   def make(initialBalance: Int): STM[Nothing, BankAccount] =
//     TRef.make(initialBalance).map(BankAccount(_))
// }

// // Main application
// object ZIOSTMExample extends ZIOAppDefault {

//   // A program to transfer money between two accounts
//   val transferProgram: ZIO[Any, String, Unit] = for {
//     account1 <- BankAccount.make(100).commit
//     account2 <- BankAccount.make(50).commit
//     _ <- Console.printLine("Starting balances:")
//     _ <- account1.checkBalance.commit.flatMap(b => Console.printLine(s"Account 1 balance: $b"))
//     _ <- account2.checkBalance.commit.flatMap(b => Console.printLine(s"Account 2 balance: $b"))
//     _ <- Console.printLine("Transferring 50 from Account 1 to Account 2...")
//     _ <- (for {
//       _ <- account1.withdraw(50)
//       _ <- account2.deposit(50)
//     } yield ()).commit
//     _ <- Console.printLine("Transfer complete. New balances:")
//     _ <- account1.checkBalance.commit.flatMap(b => Console.printLine(s"Account 1 balance: $b"))
//     _ <- account2.checkBalance.commit.flatMap(b => Console.printLine(s"Account 2 balance: $b"))
//   } yield ()

//   // The main entry point of the ZIO application
//   override def run: ZIO[Any, String, Unit] = transferProgram
// }