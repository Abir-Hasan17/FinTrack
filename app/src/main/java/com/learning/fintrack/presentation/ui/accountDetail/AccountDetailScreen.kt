package com.learning.fintrack.presentation.ui.accountDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.fintrack.data.dummy.listOfTransactions
import com.learning.fintrack.data.transaction.Transaction
import com.learning.fintrack.data.transaction.toAddTransactionDetail
import com.learning.fintrack.data.transaction.toText
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(4.dp)
        ) {

            AccountDetails()

            RecentTransactions(listOfTransactions)
        }
    }
}

@Composable
fun RecentTransactions(transactions: List<Transaction>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 4.dp)) {
        items(transactions) {
            TransactionSummeryCard(it)
        }

    }
}

@Composable
fun TransactionSummeryCard(transaction: Transaction) {
    val transactionDetail = transaction.toAddTransactionDetail()
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(top = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(.7f)) {
                Text(
                    text = transactionDetail.transactionName,
                    style = MaterialTheme.typography.labelLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Amount: " + transactionDetail.amount)
                    //Spacer(Modifier.weight(0.1f))
                    Text(text = "Date: " + transactionDetail.dateOfTransaction)
                }
            }
            Column(
                modifier = Modifier.weight(.3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = transactionDetail.transactionType.toText(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun AccountDetails() {
    ElevatedCard(colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(modifier = Modifier.padding(4.dp)) {
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
        Row {
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
                Row {
                    Text(text = "Total Income:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "$totalBalance",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row {
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
                Row {
                    Text(text = "Total Income:", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "$totalBalance",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row {
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
fun AccountDescription(AccountDetail: String? = "blah blah blah blah blah blah blah blah blah") {
    if (AccountDetail != null) {
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