package com.learning.fintrack.data.transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    //suspend function can not return Flow//
    @Query("SELECT * FROM `transactions`")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transactions` WHERE id = :id")
    fun getTransactionById(id: Int): Flow<Transaction>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

}