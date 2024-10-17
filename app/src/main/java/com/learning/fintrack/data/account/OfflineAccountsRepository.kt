package com.learning.fintrack.data.account

import com.learning.fintrack.domain.AccountRepository
import kotlinx.coroutines.flow.Flow

class OfflineAccountsRepository(private val accountDao: AccountDao) : AccountRepository {
    override suspend fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAllAccounts()
    }

    override suspend fun getAccountById(id: Int): Flow<Account> {
        return accountDao.getAccountById(id)
    }

    override suspend fun insertAccount(account: Account) {
        return accountDao.insertAccount(account)
    }

    override suspend fun updateAccount(account: Account) {
        return accountDao.updateAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        return accountDao.deleteAccount(account)
    }
}