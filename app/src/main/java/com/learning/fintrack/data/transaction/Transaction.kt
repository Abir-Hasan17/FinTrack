package com.learning.fintrack.data.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.fintrack.presentation.ui.addTransaction.AddTransactionDetail
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accountId: Int,
    val amount: Double,
    val transactionName: String,
    val transactionDescription: String? = null,
    val transactionType: String,
    val dateAdded: Long = System.currentTimeMillis(),
    val dateOfTransaction: Long = System.currentTimeMillis()
)

enum class TransactionType{
    INCOME,
    LEND,
    BORROW,
    EXPENSE,
    LEND_RETURN,
    BORROW_RETURN
}

fun TransactionType.toText() : String{
    return when(this){
        TransactionType.INCOME -> "Income"
        TransactionType.LEND -> "Lend"
        TransactionType.BORROW -> "Borrow"
        TransactionType.EXPENSE -> "Expense"
        TransactionType.LEND_RETURN -> "Lend Return"
        TransactionType.BORROW_RETURN -> "Borrow Return"
    }
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

fun Transaction.toAddTransactionDetail() : AddTransactionDetail {
    return AddTransactionDetail(
        id = id,
        accountId = accountId,
        amount = amount.toString(),
        transactionName = transactionName,
        transactionDescription = transactionDescription,
        transactionType = TransactionType.valueOf(transactionType),
        dateAdded = dateAdded,
        dateOfTransaction = toFormatedDateOfTransaction()
    )
}
