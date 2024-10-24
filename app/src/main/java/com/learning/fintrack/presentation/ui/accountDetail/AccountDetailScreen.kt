package com.learning.fintrack.presentation.ui.accountDetail

import com.learning.fintrack.presentation.ui.navigation.NavigationDestination

object AccountDetailDestination : NavigationDestination {
    override val route = "account_detail"
    const val accountIdArg = "accountId"
    val routeWithArgs = "$route/{$accountIdArg}"
}

fun AccountDetailScreen() {
}