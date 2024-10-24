package com.learning.fintrack.data.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accountId: Int,
    val amount: Double,
    val description: String,
    val transactionType: String,
    val dateAdded: Long,
    val dateOfTransaction: Long
)

enum class TransactionType{
    INCOME,
    EXPENSE,
    LEND,
    BORROW
}

fun Transaction.toFormatedDateAdded() : String{
    val date = Date(dateAdded)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun Transaction.toFormatedDateOfTransaction() : String{
    val date = Date(dateOfTransaction)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}
