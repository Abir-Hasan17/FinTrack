package com.learning.fintrack.domain

import com.learning.fintrack.data.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransactions(): Flow<List<Transaction>>

    suspend fun getTransactionById(id: Int): Flow<Transaction?>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

}