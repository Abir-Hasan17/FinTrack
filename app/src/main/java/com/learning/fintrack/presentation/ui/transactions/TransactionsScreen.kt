package com.learning.fintrack.presentation.ui.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learning.fintrack.presentation.ui.navigation.NavigationDestination

object TransactionDestination : NavigationDestination {
    override val route = "transaction"
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}

@Composable
fun TransactionScreen(modifier: Modifier = Modifier) {
    
}