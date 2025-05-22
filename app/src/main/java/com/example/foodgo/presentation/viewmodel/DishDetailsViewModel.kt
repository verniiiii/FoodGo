package com.example.foodgo.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.data.remote.api.DishApi
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.presentation.screens.home.FullDishDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    private val dishApi: DishApi,
    private val restaurantApi: RestaurantApi
) : ViewModel() {

    private val _dishState = MutableStateFlow<FullDishDTO?>(null)
    val dishState: StateFlow<FullDishDTO?> = _dishState

    private val _restaurantInfo = MutableStateFlow<RestaurantInfoDTO?>(null)
    val restaurantInfo: StateFlow<RestaurantInfoDTO?> = _restaurantInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedSizeLabel = MutableStateFlow<String?>(null)
    val selectedSizeLabel: StateFlow<String?> = _selectedSizeLabel

    fun onSizeSelected(sizeLabel: String) {
        _selectedSizeLabel.value = sizeLabel
    }


    fun loadDish(dishId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val dishResponse = dishApi.getDishById(dishId)
                val dish = dishResponse.body()
                if (dish != null) {
                    _dishState.value = dish

                    // Загружаем ресторан по ID из блюда
                    val restaurantId = dish.restaurantId
                    val restaurantResponse = restaurantApi.getRestaurantInfoById(restaurantId)
                    if (restaurantResponse.isSuccessful) {
                        _restaurantInfo.value = restaurantResponse.body()
                        println("Restaurant Info: ${restaurantResponse.body()}") // Логирование
                    } else {
                        _error.value = "Failed to load restaurant: ${restaurantResponse.message()}"
                        println("Error loading restaurant: ${restaurantResponse.message()}") // Логирование
                    }

                    if (dish.sizes.isNotEmpty()) {
                        _selectedSizeLabel.value = dish.sizes.first().sizeLabel
                    }


                    _error.value = null
                } else {
                    _error.value = "Dish not found"
                }
            } catch (e: Exception) {
                _error.value = "Failed to load dish: ${e.message}"
                println("Exception: ${e.message}") // Логирование
            } finally {
                _isLoading.value = false
            }
        }
    }
}



data class RestaurantInfoDTO(
    val name: String,
    val deliveryTimeMinutes: Int
)

