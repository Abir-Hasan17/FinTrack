package com.learning.fintrack.presentation.ui.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.domain.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
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

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class TransactionUiState(
    val transactionList: List<Transaction> = listOf(),
)