package com.learning.fintrack.data.transaction

import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineTransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    override fun getTransactionById(id: Int): Flow<Transaction?> {
        return transactionDao.getTransactionById(id)
    }

    override fun getTransactionByAccountId(accountId: Int): Flow<List<Transaction>> {
        return transactionDao.getTransactionByAccountId(accountId)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }
}