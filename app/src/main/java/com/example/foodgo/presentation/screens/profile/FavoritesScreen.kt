package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.presentation.components.ScreenHeader
import com.example.foodgo.presentation.components.profile.FavoriteItemCard
import com.example.foodgo.presentation.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onDish: (dishId: Int) -> Unit,
    onBack: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesState = viewModel.favorites.collectAsState()

    ScreenHeader("Избранное", onBackClick = onBack) {
        // Список избранного
        if (favoritesState.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Список избранного пуст", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(favoritesState.value) { dish ->
                    FavoriteItemCard(
                        dish = dish,
                        onRemoveClick = { viewModel.removeFavorite(dish.dishId) },
                        onClick = {onDish(dish.dishId)}
                    )
                }

            }
        }
    }
}





