package com.learning.fintrack.domain

import com.learning.fintrack.data.account.Account
import com.learning.fintrack.data.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
     fun getAllAccounts(): Flow<List<Account>>

     fun getAccountById(id: Int): Flow<Account>

    suspend fun insertAccount(account: Account)

    suspend fun updateAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    suspend fun updateAccountBalancesByAccountId(accountId: Int)

}