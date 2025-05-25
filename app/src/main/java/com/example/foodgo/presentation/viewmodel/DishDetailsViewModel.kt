package com.example.foodgo.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.DishApi
import com.example.foodgo.data.remote.api.FavoriteApi
import com.example.foodgo.data.remote.api.FavoriteRequestDTO
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.presentation.screens.home.FullDishDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    private val dishApi: DishApi,
    private val restaurantApi: RestaurantApi,
    private val favoriteApi: FavoriteApi,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _dishState = MutableStateFlow<FullDishDTO?>(null)
    val dishState: StateFlow<FullDishDTO?> = _dishState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _restaurantInfo = MutableStateFlow<RestaurantInfoDTO?>(null)
    val restaurantInfo: StateFlow<RestaurantInfoDTO?> = _restaurantInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedSizeLabel = MutableStateFlow<String?>(null)
    val selectedSizeLabel: StateFlow<String?> = _selectedSizeLabel

    private val _count = MutableStateFlow(1)
    val count: StateFlow<Int> = _count

    fun onSizeSelected(sizeLabel: String) {
        _selectedSizeLabel.value = sizeLabel
    }

    suspend fun isDishInCart(dishId: Int): Boolean {
        loadDish(dishId) // допустим, эта функция suspend или вызывает suspend
        val dish = dishState.first { it != null }  // дождёмся, пока dishState станет не null
        return preferencesManager.isDishInCart(dish!!.id, selectedSizeLabel.value)
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
                    } else {
                        _error.value = "Failed to load restaurant: ${restaurantResponse.message()}"
                    }

                    if (dish.sizes.isNotEmpty()) {
                        _selectedSizeLabel.value = dish.sizes.first().sizeLabel
                    }

                    // Получаем список избранных блюд и проверяем, есть ли это блюдо
                    val userId = preferencesManager.getUserId()
                    if (userId != null) {
                        val favoritesResponse = favoriteApi.getFavorites(userId)
                        if (favoritesResponse.isSuccessful) {
                            val favoritesList = favoritesResponse.body() ?: emptyList()
                            _isFavorite.value = favoritesList.any { it == dishId }
                        } else {
                            println("Ошибка при получении списка избранных: ${favoritesResponse.code()} ${favoritesResponse.message()}")
                            _isFavorite.value = false
                        }
                    } else {
                        _isFavorite.value = false
                    }

                    _error.value = null
                } else {
                    _error.value = "Dish not found"
                }
            } catch (e: Exception) {
                _error.value = "Failed to load dish: ${e.message}"
                println("Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart() {
        val dish = dishState.value ?: return
        val quantity = count.value
        val size = selectedSizeLabel.value

        preferencesManager.addToCart(dish.id, size, quantity)
    }

    fun pl(){
        _count.value++
    }

    fun min(){
        if (_count.value > 1){
            _count.value--
        }
    }




    @SuppressLint("ImplicitSamInstance")
    fun toggleFavorite(dishId: Int) {
        viewModelScope.launch {
            try {
                if (_isFavorite.value) {
                    val response = favoriteApi.removeFavorite(FavoriteRequestDTO(preferencesManager.getUserId()!!, dishId))
                    if (response.isSuccessful) {
                        println("Удаление из избранного прошло успешно: $dishId")
                        _isFavorite.value = false
                    } else {
                        println("Ошибка при удалении из избранного: ${response.code()} ${response.message()}")
                    }
                } else {
                    val response = favoriteApi.addFavorite(FavoriteRequestDTO(preferencesManager.getUserId()!!, dishId))
                    if (response.isSuccessful) {
                        println("Добавление в избранное прошло успешно: $dishId")
                        _isFavorite.value = true
                    } else {
                        println("Ошибка при добавлении в избранное: ${response.code()} ${response.message()}")
                    }
                }

                // Получаем и выводим список избранных блюд
                val favoritesResponse = favoriteApi.getFavorites(preferencesManager.getUserId()!!)
                if (favoritesResponse.isSuccessful) {
                    val favoritesList = favoritesResponse.body() ?: emptyList()
                    println("Текущий список избранных блюд: $favoritesList")
                } else {
                    println("Ошибка при получении списка избранных: ${favoritesResponse.code()} ${favoritesResponse.message()}")
                }
            } catch (e: Exception) {
                println("Ошибка при toggleFavorite: ${e.message}")
            }
        }
    }

}



data class RestaurantInfoDTO(
    val name: String,
    val deliveryTimeMinutes: Int
)

