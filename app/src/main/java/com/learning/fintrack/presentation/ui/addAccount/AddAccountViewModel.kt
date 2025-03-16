package com.learning.fintrack.presentation.ui.addAccount

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.learning.fintrack.data.account.Account
import com.learning.fintrack.domain.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    var accountUiState by mutableStateOf(AccountUiState())
        private set

    private fun validateInput(uiState: AddAccountDetails = accountUiState.addAccountDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && startingBalance.isNotBlank() && currency.isNotBlank()
        }
    }

    fun updateUiState(addAccountDetails: AddAccountDetails) {
        accountUiState = AccountUiState(
            addAccountDetails = addAccountDetails,
            isEntryValid = validateInput(addAccountDetails)
        )
    }

    suspend fun addAccount() {
        if (validateInput()) {
            accountRepository.insertAccount(accountUiState.addAccountDetails.toAccount())
        }
    }
}

data class AccountUiState(
    val addAccountDetails: AddAccountDetails = AddAccountDetails(),
    val isEntryValid: Boolean = false
)

data class AddAccountDetails(
    val name: String = "",
    val startingBalance: String = "",
    val currency: String = "",
    val description: String = ""
)

fun AddAccountDetails.toAccount(): Account = Account(
    id = 0,
    accountName = name,
    startingBalance = startingBalance.toDoubleOrNull() ?: 0.0,
    currency = currency,
    accountDescription = description ?: "",
)