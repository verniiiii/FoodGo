package com.example.foodgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.data.remote.api.OrderApi
import com.example.foodgo.data.remote.dto.order.OrderWithItemsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderApi: OrderApi
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderWithItemsDTO>>(emptyList())
    val orders: StateFlow<List<OrderWithItemsDTO>> = _orders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadOrders(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = orderApi.getOrdersByUserId(userId)
                if (response.isSuccessful) {
                    _orders.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Ошибка: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка сети: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
