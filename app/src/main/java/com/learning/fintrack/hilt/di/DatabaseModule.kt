package com.learning.fintrack.hilt.di

import android.content.Context
import com.learning.fintrack.data.account.AccountDatabase
import com.learning.fintrack.data.account.OfflineAccountsRepository
import com.learning.fintrack.data.transaction.OfflineTransactionRepository
import com.learning.fintrack.data.transaction.TransactionDatabase
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAccountDatabase(@ApplicationContext context: Context): AccountDatabase {
        return AccountDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(database: AccountDatabase): AccountRepository {
        return OfflineAccountsRepository(database.accountDao())
    }

    @Provides
    @Singleton
    fun provideTransactionDatabase(@ApplicationContext context: Context): TransactionDatabase {
        return TransactionDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(database: TransactionDatabase): TransactionRepository {
        return OfflineTransactionRepository(database.transactionDao())
    }

}