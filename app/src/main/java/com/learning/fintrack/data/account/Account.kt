package com.learning.fintrack.data.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.fintrack.presentation.ui.accountDetail.LongAccountDetails
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val startingBalance: Double,
    val balance: Double = startingBalance,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val totalBorrowed: Double = 0.0,
    val totalLent: Double = 0.0,
    val currency: String = "TK",
    val description: String? = null,
    val dateCreated: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)

fun Account.formattedStartingBalance(): String {
    return NumberFormat.getCurrencyInstance().format(startingBalance)
}

fun Account.formattedBalance(): String {
    return NumberFormat.getCurrencyInstance().format(balance)
}

fun Account.formattedTotalIncome(): String {
    return NumberFormat.getCurrencyInstance().format(totalIncome)
}

fun Account.formattedTotalExpense(): String {
    return NumberFormat.getCurrencyInstance().format(totalExpense)
}

fun Account.formattedTotalBorrowed(): String {
    return NumberFormat.getCurrencyInstance().format(totalBorrowed)
}

fun Account.formattedTotalLent(): String {
    return NumberFormat.getCurrencyInstance().format(totalLent)
}

fun Account.formatedDateCreated(): String {
    val date = Date(dateCreated)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun Account.toLongAccountDetails(): LongAccountDetails {
    return LongAccountDetails(
        id = id,
        name = name,
        startingBalance = formattedStartingBalance(),
        balance = formattedBalance(),
        totalIncome = formattedTotalIncome(),
        totalExpense = formattedTotalExpense(),
        totalBorrowed = formattedTotalBorrowed(),
        totalLent = formattedTotalLent(),
        currency = currency,
        description = description ?: "",
        dateCreated = formatedDateCreated(),
        isActive = isActive
    )
}