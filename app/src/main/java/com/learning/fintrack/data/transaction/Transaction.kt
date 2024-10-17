package com.learning.fintrack.data.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val description: String,
    val type: String,
    val dateAdded: Long,
    val dateOfTransaction: Long
)

enum class TransactionType{
    INCOME,
    EXPENSE,
    LEND,
    BORROW
}
