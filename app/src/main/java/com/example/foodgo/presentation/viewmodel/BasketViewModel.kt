package com.example.foodgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.DishApi
import com.example.foodgo.data.remote.api.OrderApi
import com.example.foodgo.data.remote.api.UserApi
import com.example.foodgo.data.remote.dto.order.CartDishDTO
import com.example.foodgo.data.remote.dto.order.OrderDTO
import com.example.foodgo.data.remote.dto.order.OrderItemDTO
import com.example.foodgo.data.remote.dto.order.OrderWithItemsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val userApi: UserApi,
    private val dishApi: DishApi,
    private val orderApi: OrderApi,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> = _cartItemCount

    private val _showOrderSuccessToast = MutableStateFlow(false)
    val showOrderSuccessToast: StateFlow<Boolean> = _showOrderSuccessToast

    fun updateCartItemCount(count: Int) {
        _cartItemCount.value = count
    }
    suspend fun getCartDishes(): List<CartDishDTO> {
        val cartItems = preferencesManager.getCartItems()
        for (d in cartItems) println(d.toString())
        val dishes = mutableListOf<CartDishDTO>()

        for ((key, _) in cartItems) {
            val parts = key.split("|")

            val dishId: Int
            val size: String

            val response: Response<CartDishDTO>?

            if (parts.size == 2) {
                dishId = parts[0].toIntOrNull() ?: continue
                size = parts[1]
                response = dishApi.getDishWithSize(dishId, size)
            } else if (parts.size == 1) {
                dishId = parts[0].toIntOrNull() ?: continue
                response = dishApi.getDishWithoutSize(dishId)

            } else {
                continue
            }

            if (response.isSuccessful) {
                response.body()?.let { dish ->
                    dishes.add(dish)
                }
            }
        }

        for (d in dishes) println(d.toString())

        return dishes
    }

    fun getCartItems(): Map<String, Int>{
        return preferencesManager.getCartItems()
    }

    fun saveCartItems(updatedCart: MutableMap<String, Int>){
        preferencesManager.saveCartItems(updatedCart)
    }


    fun createOrder(dishes: List<CartDishDTO>, quantities: Map<String, Int>, address: String) {
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserToken()
                val userResponse = userApi.getUserByToken(token!!)
                val userId = userResponse.body()?.user?.id

                if (userId == null) {
                    return@launch
                }

                val orderItems = dishes.mapNotNull { dish ->
                    val quantity = quantities["${dish.id}|${dish.size}"] ?: 0
                    if (quantity <= 0) return@mapNotNull null

                    OrderItemDTO(
                        dishId = dish.id,
                        size = dish.size ?: "",
                        quantity = quantity,
                        pricePerItem = dish.sizePrice
                    )
                }

                val totalPrice = orderItems.sumOf { it.pricePerItem * it.quantity }

                val orderDTO = OrderDTO(
                    orderId = 0,
                    userId = userId,
                    totalPrice = totalPrice,
                    address = address,
                    orderDate = getCurrentDate()
                )

                val orderRequest = OrderWithItemsDTO(order = orderDTO, items = orderItems)

                val response = orderApi.addOrder(orderRequest)

                if (response.isSuccessful) {
                    preferencesManager.clearCart()
                    _showOrderSuccessToast.value = true


                } else {

                }
            } catch (e: Exception) {

            }
        }

    }
}

fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return current.format(formatter)
}

