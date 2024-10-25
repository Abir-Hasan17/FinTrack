package com.learning.fintrack.presentation.ui.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.data.transaction.TransactionType
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val accountId: Int =
        checkNotNull(savedStateHandle[TransactionDestination.transactionIdArg])

    val uiState: StateFlow<TransactionUiState> =
        transactionRepository.getTransactionByAccountId(accountId)
            .map { TransactionUiState(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionUiState()
            )

    suspend fun deleteTransaction(transaction: Transaction){
        updateAccountInfoOnDeleteTransaction(transaction)
        transactionRepository.deleteTransaction(transaction)
    }

    suspend fun borrowReturned(transaction: Transaction){
        addTransaction(transaction.copy(transactionType = TransactionType.EXPENSE.toString()))
        deleteTransaction(transaction)
    }

    suspend fun lendReturned(transaction: Transaction) {
        deleteTransaction(transaction)
    }

    private suspend fun addTransaction(transaction: Transaction){
        transactionRepository.insertTransaction(transaction)
        updateAccountInfoOnAddTransaction(transaction = transaction)
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
                    balance = account.balance - transaction.amount
                )
            )

            TransactionType.BORROW.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalBorrowed = account.totalBorrowed + transaction.amount,
                )
            )
        }
    }

    private suspend fun updateAccountInfoOnDeleteTransaction(transaction: Transaction) {
        val account = accountRepository.getAccountById(transaction.accountId).filterNotNull().first()
        when(transaction.transactionType){
            TransactionType.INCOME.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalIncome = account.totalIncome - transaction.amount,
                    balance = account.balance - transaction.amount
                )
            )

            TransactionType.EXPENSE.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalIncome = account.totalExpense - transaction.amount,
                    balance = account.balance + transaction.amount
                )
            )

            TransactionType.BORROW.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalIncome = account.totalBorrowed - transaction.amount,
                )
            )

            TransactionType.LEND.toString() -> accountRepository.updateAccount(
                account.copy(
                    totalIncome = account.totalLent - transaction.amount,
                    balance = account.balance + transaction.amount
                )
            )
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class TransactionUiState(
    val transactionList: List<Transaction> = listOf(),
)