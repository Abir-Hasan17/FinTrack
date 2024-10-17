package com.learning.fintrack.domain

import com.learning.fintrack.data.account.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getAllAccounts(): Flow<List<Account>>

    suspend fun getAccountById(id: Int): Flow<Account>

    suspend fun insertAccount(account: Account)

    suspend fun updateAccount(account: Account)

    suspend fun deleteAccount(account: Account)

}