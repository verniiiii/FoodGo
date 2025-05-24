package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.presentation.viewmodel.FavoritesViewModel
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesState = viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp, bottom = 29.dp, start = 24.dp, end = 24.dp)
    ) {
        // Верхняя часть - заголовок
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(GreyLight, shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                // Тут можно сделать кнопку назад, если нужно
                // Для примера оставим пустым
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Избранное",
                color = Color.Black,
                fontSize = 17.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                items(favoritesState.value) { dishId ->
                    FavoriteItem(
                        dishId = dishId,
                        onRemoveClick = { viewModel.removeFavorite(dishId) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(dishId: Int, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Блюдо №$dishId", fontSize = 16.sp, color = Color.Black)

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Удалить из избранного",
            tint = Color.Red,
            modifier = Modifier
                .size(24.dp)
                .clickable { onRemoveClick() }
        )
    }
}
