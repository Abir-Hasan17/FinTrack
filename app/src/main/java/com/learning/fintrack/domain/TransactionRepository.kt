package com.learning.fintrack.domain

import com.learning.fintrack.data.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
     fun getAllTransactions(): Flow<List<Transaction>>

     fun getTransactionById(id: Int): Flow<Transaction?>

     fun getTransactionByAccountId(accountId: Int): Flow<List<Transaction>>

     suspend fun insertTransaction(transaction: Transaction)

     suspend fun updateTransaction(transaction: Transaction)

     suspend fun deleteTransaction(transaction: Transaction)

}