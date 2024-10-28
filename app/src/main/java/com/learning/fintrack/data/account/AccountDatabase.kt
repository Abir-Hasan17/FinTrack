package com.learning.fintrack.data.account

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class], version = 2, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    companion object{
        @Volatile
        private var instance: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AccountDatabase::class.java,
                    "accounts_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }

}