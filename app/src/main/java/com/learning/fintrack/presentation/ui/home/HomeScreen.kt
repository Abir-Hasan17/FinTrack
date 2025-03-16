package com.learning.fintrack.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.learning.fintrack.data.account.Account
import com.learning.fintrack.data.account.toLongAccountDetails
import com.learning.fintrack.data.dummy.listOfAccounts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val homeUiState = viewModel.homeUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Home")
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
    ) {
        Column(modifier = modifier.padding(it)) {
            if (homeUiState.value.accountList.isEmpty()) {
                Text(text = "No accounts found")
            } else {
                AccountSummeryCards(homeUiState.value.accountList)
            }
        }
    }
}

@Composable
fun AccountSummeryCards(accountList: List<Account>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(accountList) {
            AccountSummeryCard(account = it)
        }
    }
}

@Composable
fun AccountSummeryCard(account: Account) {
    val accountDetail = account.toLongAccountDetails()
    Card(
        onClick = {},
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)) {
                Column(
                    modifier = Modifier
                        .weight(.8f)
                        .padding(4.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    InfoRow(
                        infoLabel = accountDetail.name,
                        infoValue = accountDetail.dateCreated,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoRow(
                            infoLabel = "Income:",
                            infoValue = accountDetail.totalIncome,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(0.2f))
                        InfoRow(
                            infoLabel = "Credit:",
                            infoValue = accountDetail.totalLent,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoRow(
                            infoLabel = "Expense:",
                            infoValue = accountDetail.totalExpense,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(0.2f))
                        InfoRow(
                            infoLabel = "Debt:",
                            infoValue = accountDetail.totalBorrowed,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            Row {
                InfoRow(
                    infoLabel = "",
                    infoValue = accountDetail.description,
                    modifier = Modifier.weight(.7f)
                )

                VerticalDivider()
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(.3f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "Balance:")
                    Text(text = accountDetail.balance)
                }
            }
        }
    }
}

@Composable
fun InfoRow(modifier: Modifier = Modifier, infoLabel: String, infoValue: String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (infoLabel != "") Text(text = infoLabel)
        Text(text = infoValue)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}





