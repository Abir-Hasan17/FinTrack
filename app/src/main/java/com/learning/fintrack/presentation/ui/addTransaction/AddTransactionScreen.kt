package com.learning.fintrack.presentation.ui.addTransaction

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learning.fintrack.data.transaction.TransactionType
import com.learning.fintrack.data.transaction.toText
import com.learning.fintrack.presentation.ui.addAccount.InputFieldNumberBased
import com.learning.fintrack.presentation.ui.addAccount.InputFieldTextBased
import com.learning.fintrack.presentation.ui.navigation.NavigationDestination
import java.util.Date
import java.util.Locale

object AddTransactionDestination : NavigationDestination {
    override val route = "add_transaction"
    const val accountIdArg = "accountId"
    val routeWithArgs = "$route/{$accountIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(modifier: Modifier = Modifier) {
//    val viewModel: AddTransactionViewModel = hiltViewModel()
//    val addTransactionUiState = viewModel.transactionUiState
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
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //TransactionName
            InputFieldTextBased(
                label = "Transaction Name",
                placeholder = "Ex: Food",
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            //Transaction Amount
            InputFieldNumberBased(
                label = "Transaction amount",
                placeHolder = "Ex: 1000",
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
//                    viewModel.updateUiState(
//                        addAccountUiState.addAccountDetails.copy(
//                            startingBalance = it.toString()
//                        )
//                    )
                }
            )

            //Transaction type selector
            var selectedType by remember { mutableStateOf(TransactionType.INCOME)}
            TransactionTypeSelector(selectedType = selectedType, onTypeSelected = {})

            //Transaction date
            var selectedDate by remember { mutableStateOf<Long?>(null) }
            var showModal by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = selectedDate?.let { convertMillisToDate(it) } ?: "",
                onValueChange = { },
                label = { Text("Transaction Date") },
                placeholder = { Text("MM/DD/YYYY") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .pointerInput(selectedDate) {
                        awaitEachGesture {
                            // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                            // in the Initial pass to observe events before the text field consumes them
                            // in the Main pass.
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showModal = true
                            }
                        }
                    }
            )

            if (showModal) {
                DatePickerModal(
                    onDateSelected = { selectedDate = it },
                    onDismiss = { showModal = false }
                )
            }

            //TransactionDescription
            InputFieldTextBased(
                label = "Transaction Description",
                placeholder = "Ex: Vat, Dal and Shobji",
                value = "",
                modifier = Modifier.height(150.dp).fillMaxWidth(),
                onValueChange = {}
            )

            //Add Button
            Spacer(Modifier.size(12.dp))
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Add Transaction")
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Transaction Type", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(TransactionType.entries.size) { index ->
                val type = TransactionType.entries[index]
                Column(
                    modifier = Modifier
                        .clickable { onTypeSelected(type) }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        selected = (type == selectedType),
                        onClick = { onTypeSelected(type) }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = type.toText(), fontSize = 12.sp)
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTransactionScreenPreview() {
    AddTransactionScreen()
}

