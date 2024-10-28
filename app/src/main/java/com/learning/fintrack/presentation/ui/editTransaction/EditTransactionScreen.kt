package com.learning.fintrack.presentation.ui.editTransaction

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learning.fintrack.presentation.ui.navigation.NavigationDestination

object EditTransactionDestination : NavigationDestination {
    override val route: String = "edit_transaction"
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}

@Composable
fun EditTransactionScreen(modifier: Modifier) {

}