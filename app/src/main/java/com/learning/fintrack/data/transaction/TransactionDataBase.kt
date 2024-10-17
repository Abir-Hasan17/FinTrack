package com.learning.fintrack.data.transaction

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class TransactionDataBase : RoomDatabase(){

    abstract fun transactionDao(): TransactionDao

    companion object{
        @Volatile
        private var instance: TransactionDataBase? = null

        fun getDatabase(context: Context): TransactionDataBase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TransactionDataBase::class.java,
                    "transactions_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}