package com.learning.fintrack.presentation.ui.addTransaction

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learning.fintrack.presentation.ui.navigation.NavigationDestination

object AddTransactionDestination : NavigationDestination {
    override val route = "add_transaction"
    const val accountIdArg = "accountId"
    val routeWithArgs = "$route/{$accountIdArg}"
}

@Composable
fun AddTransactionScreen(modifier: Modifier = Modifier) {

}