package com.learning.fintrack.presentation.ui.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.data.transaction.TransactionType
import com.learning.fintrack.data.transaction.toFormatedDateOfTransaction
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

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
        accountRepository.updateAccountBalancesByAccountId(transaction.accountId)
    }

    suspend fun borrowReturned(transaction: Transaction) {
        addTransaction(
            transaction.copy(
                transactionType = TransactionType.BORROW_RETURN.toString(),
                amount = transaction.amount,
                transactionName = "${transaction.transactionName} returned!!",
                transactionDescription = "Borrow transactionName: Was Borrowed on ${transaction.toFormatedDateOfTransaction()} \n" +
                        " ${transaction.transactionDescription}"
            )
        )
        deleteTransaction(transaction)
    }

    suspend fun lendReturned(transaction: Transaction) {
        addTransaction(
            transaction.copy(
                transactionType = TransactionType.LEND_RETURN.toString(),
                amount = transaction.amount,
                transactionName = "${transaction.transactionName} returned!!",
                transactionDescription = "Lend transactionName: Was Lent on ${transaction.toFormatedDateOfTransaction()} \n" +
                        " ${transaction.transactionDescription}"
            )
        )
        deleteTransaction(transaction)
    }

    suspend fun lendExpended(transaction: Transaction) {
        addTransaction(
            transaction.copy(
                transactionType = TransactionType.EXPENSE.toString(),
                amount = transaction.amount,
                transactionName = "${transaction.transactionName} expended!!",
                transactionDescription = "Lend Expended that was taken on ${transaction.toFormatedDateOfTransaction()}"
            )
        )
    }

    private suspend fun addTransaction(transaction: Transaction) {
        transactionRepository.insertTransaction(transaction)
        accountRepository.updateAccountBalancesByAccountId(transaction.accountId)
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class TransactionUiState(
    val transactionList: List<Transaction> = listOf(),
    val selectedTransactionId: Int = -1,
)