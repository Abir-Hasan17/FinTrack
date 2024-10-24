package com.learning.fintrack.presentation.ui.addTransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.fintrack.data.account.Account
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.data.transaction.TransactionType
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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

    suspend fun addTransaction() {
        if (validateInput()) {
            transactionRepository.insertTransaction(transactionUiState.addTransactionDetail.toTransaction())

            updateAccountInfo()
        }
    }

    private suspend fun updateAccountInfo(){
        val account = accountRepository.getAccountById(accountId).filterNotNull().first()
        when (transactionUiState.addTransactionDetail.transactionType) {
            TransactionType.INCOME -> accountRepository.updateAccount(
                account.copy(
                    totalIncome = account.totalIncome + transactionUiState.addTransactionDetail.amount.toDouble(),
                    balance = account.balance + transactionUiState.addTransactionDetail.amount.toDouble()
                )
            )

            TransactionType.EXPENSE -> accountRepository.updateAccount(
                account.copy(
                    totalExpense = account.totalExpense + transactionUiState.addTransactionDetail.amount.toDouble(),
                    balance = account.balance - transactionUiState.addTransactionDetail.amount.toDouble()
                )
            )

            TransactionType.LEND -> accountRepository.updateAccount(
                account.copy(
                    totalLent = account.totalLent + transactionUiState.addTransactionDetail.amount.toDouble(),
                )
            )

            TransactionType.BORROW -> accountRepository.updateAccount(
                account.copy(
                    totalBorrowed = account.totalBorrowed + transactionUiState.addTransactionDetail.amount.toDouble(),
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