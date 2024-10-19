package com.learning.fintrack.presentation.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val homeUiState = viewModel.homeUiState.collectAsState()

    if (homeUiState.value.accountList.isEmpty()) {
        Text(text = "No accounts found")
    }
}

