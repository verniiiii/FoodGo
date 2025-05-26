package com.example.foodgo.presentation.screens.profile

import com.example.foodgo.presentation.viewmodel.OrdersViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.data.remote.dto.order.OrderWithItemsDTO
import com.example.foodgo.PreferencesManager
import com.example.foodgo.presentation.components.OrderItemCard
import com.example.foodgo.presentation.components.ScreenHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    preferencesManager: PreferencesManager,
    onOrderClick: (OrderWithItemsDTO) -> Unit,
    onBack: () -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val userId = preferencesManager.getUserId()
    val orders by viewModel.orders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOrders(userId)
    }

    ScreenHeader("Мои заказы", onBackClick = onBack) {
        Box(modifier = Modifier.padding()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "Ошибка",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(orders) { orderWithItems ->
                            OrderItemCard(orderWithItems, onClick = { onOrderClick(orderWithItems) })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

