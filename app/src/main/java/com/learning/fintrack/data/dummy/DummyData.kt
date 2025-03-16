package com.learning.fintrack.data.dummy

import com.learning.fintrack.data.account.Account

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