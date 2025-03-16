package com.learning.fintrack.presentation.ui.accountDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.fintrack.presentation.ui.home.HomeScreen
import com.learning.fintrack.presentation.ui.navigation.NavigationDestination

object AccountDetailDestination : NavigationDestination {
    override val route = "account_detail"
    const val accountIdArg = "accountId"
    val routeWithArgs = "$route/{$accountIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Account Name")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { scaffoldPadding ->
        Column(modifier = Modifier.padding(scaffoldPadding).padding(8.dp)) {
            //Account Description
            AccountDescription()
            Spacer(Modifier.height(8.dp))

            //Date Created
            Text(text = "Date Created:", style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))

            //Account Balance
            AccountBalances()
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun AccountBalances(totalBalance: Double = 0.00) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row{
            Text(text = "Total Balance:", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "$totalBalance",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column {
                Row{
                    Text(text = "Total Income:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "$totalBalance",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row{
                    Text(text = "Total Expense:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "$totalBalance",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Row{
                    Text(text = "Total Income:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "$totalBalance",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row{
                    Text(text = "Total Expense:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "$totalBalance",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Spacer(Modifier.weight(.2f))
        }
    }
}

@Composable
fun AccountDescription(AccountDetail: String? = "blah blah blah blah blah blah blah blah blah"){
    if(AccountDetail != null) {
        Column {
            Text(text = "Details:", style = MaterialTheme.typography.labelLarge)
            Text(
                text = "Account Details blah blah blah",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAccountScreenPreview() {
    AccountDetailScreen()
}