package com.learning.fintrack.presentation.ui.addTransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.data.transaction.TransactionType
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val accountId: Int = checkNotNull(savedStateHandle[AddTransactionDestination.accountIdArg])
    var transactionUiState by mutableStateOf(AddTransactionUiState())
        private set

    private fun validateInput(uiState: AddTransactionDetail = transactionUiState.addTransactionDetail): Boolean {
        return with(uiState) {
            amount.isNotBlank() && description.isNotBlank() && dateOfTransaction.isNotBlank()
        }
    }

    fun updateUiState(addTransactionDetail: AddTransactionDetail) {
        transactionUiState = AddTransactionUiState(
            addTransactionDetail = addTransactionDetail,
            isEntryValid = validateInput(addTransactionDetail)
        )
    }

    suspend fun addTransactionFromAddTransactionScreen() {
        if (validateInput()) {
            addTransaction(transaction = transactionUiState.addTransactionDetail.toTransaction())
            resetUiState()
        }
    }

    private suspend fun addTransaction(transaction: Transaction){
        transactionRepository.insertTransaction(transaction)
        updateAccountInfoOnAddTransaction(transaction = transaction)
    }

    private fun resetUiState() {
        transactionUiState = AddTransactionUiState()
    }

    private suspend fun updateAccountInfoOnAddTransaction(transaction: Transaction){
        val account = accountRepository.getAccountById(transaction.accountId).filterNotNull().first()
        when (transaction.transactionType) {
            TransactionType.INCOME.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalIncome = account.totalIncome + transaction.amount,
                    balance = account.balance + transaction.amount
                )
            )

            TransactionType.EXPENSE.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalExpense = account.totalExpense + transaction.amount,
                    balance = account.balance - transaction.amount
                )
            )

            TransactionType.LEND.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalLent = account.totalLent + transaction.amount,
                )
            )

            TransactionType.BORROW.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalBorrowed = account.totalBorrowed + transaction.amount,
                )
            )
        }
    }
}

data class AddTransactionUiState(
    val addTransactionDetail: AddTransactionDetail = AddTransactionDetail(),
    val isEntryValid: Boolean = false
)

data class AddTransactionDetail(
    val id: Int = 0,
    val accountId: Int = 0,
    val amount: String = "",
    val description: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val dateAdded: Long = System.currentTimeMillis(),
    val dateOfTransaction: String = "",
)

fun AddTransactionDetail.toTransaction(): Transaction = Transaction(
    id = id,
    accountId = accountId,
    amount = amount.toDoubleOrNull() ?: 0.0,
    description = description,
    transactionType = transactionType.toString(),
    dateAdded = dateAdded,
    dateOfTransaction = dateOfTransaction.toLong()
)