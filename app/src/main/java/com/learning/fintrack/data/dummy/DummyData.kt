package com.learning.fintrack.data.dummy

import com.learning.fintrack.data.account.Account
import com.learning.fintrack.data.transaction.Transaction

val account1 = Account(
    id = 1,
    accountName = "Cash",
    startingBalance = 1000.0,
    balance = 1000.0,
    totalIncome = 1000.0,
    totalExpense = 1000.0,
    totalBorrowed = 1000.0,
    totalLent = 1000.0,
    currency = "TK",
    accountDescription = "blah blah"
)

val listOfAccounts = listOf(account1, account1.copy(id = 2, accountName = "Card"), account1.copy(id = 3, accountName = "Bank"))

val transaction1 = Transaction(
    accountId = 1,
    id = 1,
    amount = 100.0,
    transactionName = "food",
    transactionDescription = "vat dal dim vaji",
    transactionType = "EXPENSE"
)
val listOfTransactions = listOf(transaction1, transaction1.copy(id = 2, transactionName = "food2"), transaction1.copy(id = 3, transactionName = "food3"))