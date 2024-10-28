package com.learning.fintrack.data.account

import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.data.transaction.TransactionType
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OfflineAccountsRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionRepository: TransactionRepository
) : AccountRepository {
    override fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAllAccounts()
    }

    override fun getAccountById(id: Int): Flow<Account> {
        return accountDao.getAccountById(id)
    }

    override suspend fun insertAccount(account: Account) {
        accountDao.insertAccount(account)
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    override suspend fun updateAccountBalancesByAccountId(accountId: Int) {
        val account = getAccountById(accountId).filterNotNull().first()
        val currentTransactions = transactionRepository.getTransactionByAccountId(accountId).filterNotNull().first()
        var newTotalIncome = 0.0
        var newTotalExpense = 0.0
        var newTotalBorrowed = 0.0
        var newTotalLent = 0.0
        var totalBorrowReturn = 0.0

        for (transaction in currentTransactions) {
            when(transaction.transactionType){
                TransactionType.INCOME.toString() -> newTotalIncome += transaction.amount
                TransactionType.EXPENSE.toString() -> newTotalExpense += transaction.amount
                TransactionType.BORROW.toString() -> newTotalBorrowed += transaction.amount
                TransactionType.LEND.toString() -> newTotalLent += transaction.amount
                TransactionType.BORROW_RETURN.toString() -> totalBorrowReturn += transaction.amount
            }
        }
        val newBalance = newTotalIncome - newTotalExpense - totalBorrowReturn

        updateAccount(
            account.copy(
                totalIncome = newTotalIncome,
                totalExpense = newTotalExpense,
                totalBorrowed = newTotalBorrowed,
                totalLent = newTotalLent,
                balance = newBalance
            )
        )
    }

}