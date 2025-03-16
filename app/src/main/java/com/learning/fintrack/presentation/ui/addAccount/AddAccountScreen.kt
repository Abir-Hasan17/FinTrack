package com.learning.fintrack.presentation.ui.addAccount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreen(modifier: Modifier = Modifier) {
    val viewModel: AddAccountViewModel = hiltViewModel()
    val addAccountUiState = viewModel.accountUiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Transaction")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //AccountName
            InputFieldTextBased(
                label = "Account Name",
                placeholder = "Ex: January Account",
                value = addAccountUiState.addAccountDetails.name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.updateUiState(
                        addAccountUiState.addAccountDetails.copy(
                            name = it.toString()
                        )
                    )
                }
            )

            //AccountDescription
            InputFieldTextBased(
                label = "Account Description",
                placeholder = "Ex: Tracking the expenses of January",
                value = addAccountUiState.addAccountDetails.description,
                modifier = Modifier.height(180.dp).fillMaxWidth(),
                onValueChange = {
                    viewModel.updateUiState(
                        addAccountUiState.addAccountDetails.copy(
                            description = it
                        )
                    )
                }
            )

            //InitialBalance
            InputFieldNumberBased(
                label = "Initial Balance",
                placeHolder = "Ex: 1000",
                value = addAccountUiState.addAccountDetails.startingBalance,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.updateUiState(
                        addAccountUiState.addAccountDetails.copy(
                            startingBalance = it.toString()
                        )
                    )
                }
            )

            //Currency
            InputFieldTextBased(
                label = "Currency",
                placeholder = "Ex: TK",
                value = addAccountUiState.addAccountDetails.currency,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.updateUiState(
                        addAccountUiState.addAccountDetails.copy(
                            currency = it.toString()
                        )
                    )
                }
            )

            Spacer(Modifier)
            val coroutine = rememberCoroutineScope()
            Button(
                onClick = {
                    coroutine.launch {
                        viewModel.addAccount()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Account")
            }
        }
    }
}

@Composable
fun InputFieldTextBased(
    label: String,
    placeholder: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = modifier
    )
}

@Composable
fun InputFieldNumberBased(
    label: String,
    placeHolder: String,
    value: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        placeholder = { Text(placeHolder) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTransactionScreenPreview() {
    AddAccountScreen()
}