package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.foodgo.data.remote.dto.dish.FullDishDTO
import com.example.foodgo.data.remote.dto.order.OrderItemDTO
import com.example.foodgo.presentation.components.ScreenHeader
import com.example.foodgo.presentation.components.profile.OrderItemCard
import com.example.foodgo.presentation.viewmodel.OrderDetailsViewModel
import com.example.foodgo.ui.theme.GreyLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: Long,
    onBack: () -> Unit,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    // Устанавливаем orderId при инициализации или изменении
    LaunchedEffect(orderId) {
        viewModel.setOrderId(orderId)
    }

    ScreenHeader("Детали заказа", onBackClick = onBack) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding()) {
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



