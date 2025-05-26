package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.data.remote.dto.order.OrderItemDTO
import com.example.foodgo.presentation.viewmodel.OrderDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: Long,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    // Устанавливаем orderId при инициализации или изменении
    LaunchedEffect(orderId) {
        viewModel.setOrderId(orderId)
    }

    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        items(state.items) { item ->
                            OrderItemCard(item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(item: OrderItemDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Dish ID: ${item.dishId}")
            Text(text = "Size: ${item.size}")
            Text(text = "Quantity: ${item.quantity}")
            Text(text = "Price per item: $${item.pricePerItem}")
        }
    }
}
