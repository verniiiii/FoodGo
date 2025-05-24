package com.example.foodgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.FavoriteApi
import com.example.foodgo.data.remote.api.FavoriteRequestDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val favoriteApi: FavoriteApi
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Int>>(emptyList())
    val favorites: StateFlow<List<Int>> = _favorites

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val userId = preferencesManager.getUserId() ?: return@launch
            val response = favoriteApi.getFavorites(userId)
            if (response.isSuccessful) {
                _favorites.value = response.body() ?: emptyList()
            } else {
                _favorites.value = emptyList()
            }
        }
    }

    fun removeFavorite(dishId: Int) {
        viewModelScope.launch {
            val userId = preferencesManager.getUserId() ?: return@launch
            val response = favoriteApi.removeFavorite(FavoriteRequestDTO(userId, dishId))
            if (response.isSuccessful) {
                // Обновим список после удаления
                _favorites.value = _favorites.value.filter { it != dishId }
            }
        }
    }
}
