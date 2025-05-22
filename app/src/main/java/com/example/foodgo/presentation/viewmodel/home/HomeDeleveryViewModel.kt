package com.example.foodgo.presentation.viewmodel.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.data.remote.dto.CategoryDTO
import com.example.foodgo.data.remote.dto.RestaurantWithPhotosDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val restaurantApi: RestaurantApi
) : ViewModel() {

    private val _restaurants = MutableStateFlow<List<RestaurantWithPhotosDTO>>(emptyList())
    val restaurants: StateFlow<List<RestaurantWithPhotosDTO>> = _restaurants

    private val _categories = MutableStateFlow<List<CategoryDTO>>(emptyList())
    val categories: StateFlow<List<CategoryDTO>> = _categories


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _filterCriteria = MutableStateFlow<FilterCriteria?>(null)
    val filterCriteria: StateFlow<FilterCriteria?> = _filterCriteria

    fun applyFilters(criteria: FilterCriteria) {
        _filterCriteria.value = criteria
    }
    data class FilterCriteria(
        val minRating: Int,
        val maxDeliveryTime: Int // в минутах
    )


    init {
        fetchRestaurants()
        fetchCategories()
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = restaurantApi.getRestaurants()
                if (response.isSuccessful) {
                    _restaurants.value = response.body().orEmpty()
                } else {
                    _error.value = "Ошибка сервера: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = restaurantApi.getCategories()
                if (response.isSuccessful) {
                    _categories.value = response.body().orEmpty()
                    Log.d("HomeViewModel", "Полученные категории: ${_categories.value}")
                } else {
                    _error.value = "Ошибка при получении категорий: ${response.code()}"
                    Log.e("HomeViewModel", "Ошибка при получении категорий: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.value = "Ошибка категорий: ${e.message}"
                Log.e("HomeViewModel", "Ошибка категорий", e)
            }
        }
    }

    fun filterRestaurants(
        restaurantList: List<RestaurantWithPhotosDTO>,
        selectedCategory: String,
        searchQuery: String,
        filterCriteria: HomeViewModel.FilterCriteria?
    ): List<RestaurantWithPhotosDTO> {
        val baseFilter = if (selectedCategory == "Всё") {
            restaurantList.filter { restaurant ->
                isRestaurantOpen(restaurant.openingTime, restaurant.closingTime)
            }
        } else {
            restaurantList.filter { restaurant ->
                restaurant.categories.contains(selectedCategory) &&
                        isRestaurantOpen(restaurant.openingTime, restaurant.closingTime)
            }
        }

        val searchFiltered = if (searchQuery.isBlank()) {
            baseFilter
        } else {
            baseFilter.filter { restaurant ->
                restaurant.name.contains(searchQuery, ignoreCase = true) ||
                        restaurant.categories.any { it.contains(searchQuery, ignoreCase = true) }
            }
        }

        return filterCriteria?.let { criteria ->
            searchFiltered.filter { restaurant ->
                restaurant.rating >= criteria.minRating &&
                        restaurant.deliveryTimeMinutes <= criteria.maxDeliveryTime
            }
        } ?: searchFiltered
    }

    fun refreshData() {
        fetchRestaurants()
        fetchCategories()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun isRestaurantOpen(openingTime: String, closingTime: String): Boolean {
        val currentTime = LocalTime.now()
        val openTime = LocalTime.parse(openingTime)
        val closeTime = LocalTime.parse(closingTime)

        //return !currentTime.isBefore(openTime) && !currentTime.isAfter(closeTime)
        return true
    }

}
