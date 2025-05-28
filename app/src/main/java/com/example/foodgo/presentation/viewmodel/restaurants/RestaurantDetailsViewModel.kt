package com.example.foodgo.presentation.viewmodel.restaurants

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.R
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.data.remote.dto.dish.DishDTO
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RestaurantDetailsUiState(
    val imageUrls: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val categories: List<String> = emptyList(),
    val dishes: List<DishDTO> = emptyList()
)

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val api: RestaurantApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantDetailsUiState())
    val uiState: StateFlow<RestaurantDetailsUiState> = _uiState

    fun loadRestaurantData(restaurant: RestaurantWithPhotosDTO, context: Context) {
        viewModelScope.launch {
            val imageUrls = if (restaurant.photos.isNullOrEmpty()) {
                listOf(context.getString(R.string.rest_no_url))
            } else {
                restaurant.photos
            }

            _uiState.update { it.copy(imageUrls = imageUrls) }

            val response = api.getDishesByRestaurant(restaurant.id)
            val dishes = response.body().orEmpty()
            val uniqueCategories = dishes.mapNotNull { it.category }.distinct() + context.getString(R.string.other)

            _uiState.update {
                it.copy(
                    dishes = dishes,
                    categories = uniqueCategories,
                    selectedCategory = uniqueCategories.firstOrNull()
                )
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update {
            if (it.selectedCategory == category) it
            else it.copy(selectedCategory = category)
        }
    }

}

