package com.example.foodgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.DishApi
import com.example.foodgo.data.remote.api.FavoriteApi
import com.example.foodgo.data.remote.api.FavoriteRequestDTO
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.data.remote.dto.dish.FavoriteDishInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val favoriteApi: FavoriteApi,
    private val dishApi: DishApi,
    private val restaurantApi: RestaurantApi
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteDishInfo>>(emptyList())
    val favorites: StateFlow<List<FavoriteDishInfo>> = _favorites

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val userId = preferencesManager.getUserId() ?: return@launch

            val favoriteIdsResponse = favoriteApi.getFavorites(userId)
            if (!favoriteIdsResponse.isSuccessful) return@launch
            val favoriteIds = favoriteIdsResponse.body() ?: emptyList()

            val favoriteDishes = favoriteIds.mapNotNull { dishId ->
                val dishResponse = dishApi.getDishById(dishId)
                if (!dishResponse.isSuccessful) return@mapNotNull null
                val dish = dishResponse.body() ?: return@mapNotNull null

                val restaurantResponse = restaurantApi.getRestaurantInfoById(dish.restaurantId)
                val restaurantName = restaurantResponse.body()?.name ?: "Неизвестный ресторан"

                FavoriteDishInfo(
                    dishId = dish.id,
                    name = dish.name,
                    price = dish.basePrice,
                    imageUrl = dish.photoUrl,
                    restaurantName = restaurantName
                )
            }

            _favorites.value = favoriteDishes
        }
    }

    fun removeFavorite(dishId: Int) {
        viewModelScope.launch {
            val userId = preferencesManager.getUserId() ?: return@launch
            val response = favoriteApi.removeFavorite(FavoriteRequestDTO(userId, dishId))
            if (response.isSuccessful) {
                _favorites.value = _favorites.value.filter { it.dishId != dishId }
            }
        }
    }
}



