package com.learning.fintrack.data.transaction

import com.learning.fintrack.domain.TransactionRepository
import kotlinx.coroutines.flow.Flow

class OfflineTransactionRepository(private val transactionDao: TransactionDao): TransactionRepository {
    override suspend fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    override suspend fun getTransactionById(id: Int): Flow<Transaction?> {
        return transactionDao.getTransactionById(id)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        return transactionDao.insertTransaction(transaction)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        return transactionDao.updateTransaction(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        return transactionDao.deleteTransaction(transaction)
    }
}