package com.learning.fintrack.presentation.ui.accountDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.fintrack.data.account.Account
import com.learning.fintrack.data.account.toLongAccountDetails
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.domain.AccountRepository
import com.learning.fintrack.domain.TransactionRepository
import com.learning.fintrack.presentation.ui.home.HomeScreenViewModel
import com.learning.fintrack.presentation.ui.home.HomeScreenViewModel.Companion
import com.learning.fintrack.presentation.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val accountId: Int =
        checkNotNull(savedStateHandle[AccountDetailDestination.accountIdArg])

    val accountDetailUiState: StateFlow<AccountDetailUiState> = accountRepository.getAccountById(accountId)
        .map { AccountDetailUiState(it.toLongAccountDetails()) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = AccountDetailUiState()
        )

    val recentTransactionsUiState: StateFlow<RecentTransactionsUiState> =
        transactionRepository.getTransactionByAccountId(accountId).map { RecentTransactionsUiState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = RecentTransactionsUiState()
        )

    suspend fun deleteAccount(){
        accountRepository.deleteAccount(accountDetailUiState.value.longAccountDetails.toAccount())
    }

    suspend fun deactivateAccount(){
        accountRepository.updateAccount(accountDetailUiState.value.longAccountDetails.toAccount().copy(isActive = false))
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class AccountDetailUiState(
    val longAccountDetails: LongAccountDetails = LongAccountDetails(),
)

data class RecentTransactionsUiState(
    val recentTransactions: List<Transaction> = listOf()
)

data class LongAccountDetails(
    val id: Int = 0,
    val name: String = "",
    val startingBalance: String = "",
    val balance: String = "",
    val totalIncome: String = "",
    val totalExpense: String = "",
    val totalBorrowed: String = "",
    val totalLent: String = "",
    val currency: String = "",
    val description: String = "",
    val dateCreated: String = "",
    val isActive: Boolean = true
)

fun LongAccountDetails.toAccount() = Account(
    id = id,
    accountName = name,
    startingBalance = startingBalance.toDouble(),
    balance = balance.toDouble(),
    totalIncome = totalIncome.toDouble(),
    totalExpense = totalExpense.toDouble(),
    totalBorrowed = totalBorrowed.toDouble(),
    totalLent = totalLent.toDouble(),
    currency = currency,
    accountDescription = description,
    dateCreated = dateCreated.toLong(),
    isActive = isActive
)

