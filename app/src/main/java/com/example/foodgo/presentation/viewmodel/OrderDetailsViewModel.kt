package com.example.foodgo.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.OrderApi
import com.example.foodgo.data.remote.dto.order.OrderItemDTO
import com.example.foodgo.data.remote.dto.order.OrderWithItemsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class OrderDetailsUiState(
    val isLoading: Boolean = true,
    val items: List<OrderItemDTO> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderApi: OrderApi,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailsUiState())
    val uiState: StateFlow<OrderDetailsUiState> = _uiState

    private val _orderId = MutableStateFlow<Long?>(null)
    val orderId: StateFlow<Long?> = _orderId.asStateFlow()

    private val userId = preferencesManager.getUserId()

    fun setOrderId(id: Long) {
        _orderId.value = id
        loadOrderDetails()
    }

    private fun loadOrderDetails() {
        val currentOrderId = _orderId.value ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val userOrdersResponse = orderApi.getOrdersByUserId(userId)
                if (userOrdersResponse.isSuccessful) {
                    val orders = userOrdersResponse.body()
                    val order = orders?.find { it.order.orderId == currentOrderId }

                    if (order != null) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                items = order.items
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(isLoading = false, error = "Order not found")
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Failed to fetch orders")
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.localizedMessage)
                }
            }
        }
    }
}