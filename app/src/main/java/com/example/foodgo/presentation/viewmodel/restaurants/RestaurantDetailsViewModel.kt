package com.example.foodgo.presentation.viewmodel.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val dishes: List<DishDTO> = emptyList()  // <-- добавляем список блюд
)

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val api: RestaurantApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantDetailsUiState())
    val uiState: StateFlow<RestaurantDetailsUiState> = _uiState

    fun loadRestaurantData(restaurant: RestaurantWithPhotosDTO) {
        viewModelScope.launch {
            val imageUrls = if (restaurant.photos.isNullOrEmpty()) {
                listOf("https://yastatic.net/naydex/yandex-search/b1sNx6865/ea576csEb/zpEWAUjQ0uvJh4njjmjZwqLAKiVOM57P3VdVY2NLN5HPCKpPBd-qkJdMAfG_IcLz-eUI2tK-rO34wARthPf1f8LZAkR5zdaesNKRgt5I1daqtV8pCkL23qk-XBIfDkrx4wi2qp1TNgE6sZQ0Z4g_9qXMWMf-06HoTCw")
            } else {
                restaurant.photos
            }

            _uiState.update { it.copy(imageUrls = imageUrls) }

            val response = api.getDishesByRestaurant(restaurant.id)
            val dishes = response.body().orEmpty()
            val uniqueCategories = dishes.mapNotNull { it.category }.distinct() +  "Другое"

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
        println("Selected category changed to: ${uiState.value.selectedCategory}")
    }

}

