package com.learning.fintrack.presentation.ui.editTransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.fintrack.data.transaction.toAddTransactionDetail
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import com.learning.fintrack.presentation.ui.addTransaction.AddTransactionDetail
import com.learning.fintrack.presentation.ui.addTransaction.AddTransactionUiState
import com.learning.fintrack.presentation.ui.addTransaction.toTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val transactionId: Int = checkNotNull(savedStateHandle[EditTransactionDestination.transactionIdArg])
    var transactionUiState by mutableStateOf(AddTransactionUiState())
        private set

    init {
        viewModelScope.launch {
            val transaction = transactionRepository.getTransactionById(transactionId).filterNotNull().first()
            transactionUiState = AddTransactionUiState(transaction.toAddTransactionDetail())
        }
    }

    private fun validateInput(uiState: AddTransactionDetail = transactionUiState.addTransactionDetail): Boolean {
        return with(uiState) {
            amount.isNotBlank() && transactionName.isNotBlank() && dateOfTransaction.isNotBlank()
        }
    }

    fun updateUiState(addTransactionDetail: AddTransactionDetail) {
        transactionUiState = AddTransactionUiState(
            addTransactionDetail = addTransactionDetail,
            isEntryValid = validateInput(addTransactionDetail)
        )
    }

    suspend fun updateTransactionFromEditTransactionScreen() {
        if (validateInput()) {
            transactionRepository.updateTransaction(transactionUiState.addTransactionDetail.toTransaction())
            accountRepository.updateAccountBalancesByAccountId(transactionUiState.addTransactionDetail.accountId)
            resetUiState()
        }
    }

    private fun resetUiState() {
        transactionUiState = AddTransactionUiState()
    }

}
