package com.example.foodgo.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R

import androidx.compose.ui.graphics.Color // Убедитесь, что используете правильный импорт
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.foodgo.data.remote.dto.RestaurantWithPhotosDTO
import com.example.foodgo.presentation.components.restaurants.CategoryButton
import com.example.foodgo.presentation.components.restaurants.DishCard
import com.example.foodgo.presentation.viewmodel.DishDetailsViewModel
import com.example.foodgo.presentation.viewmodel.restaurants.RestaurantDetailsViewModel
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@Composable
fun RestaurantDetailsScreen(
    restaurant: RestaurantWithPhotosDTO,
    onDishDetail: (dish_id: Int) -> Unit,
    onBack: () -> Unit,
    viewModel: RestaurantDetailsViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.loadRestaurantData(restaurant)
    }

    val pagerState = rememberPagerState()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value.selectedCategory) {
        println("Selected category changed to: ${uiState.value.selectedCategory}")
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(321.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        HorizontalPager(
            count = uiState.value.imageUrls.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = uiState.value.imageUrls[page],
                contentDescription = "Restaurant Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (uiState.value.imageUrls.size > 1) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
            ) {
                repeat(uiState.value.imageUrls.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 7.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (index == pagerState.currentPage) White else PlaceholderGrey)
                    )
                }
            }
        }

        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = IconGrey3,
                modifier = Modifier.size(18.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 321.dp, start = 24.dp, end = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.star1),
                    contentDescription = "Rating",
                    modifier = Modifier.size(20.dp),
                    tint = Orange
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = restaurant.rating.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = IconGrey3
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.delivery),
                    contentDescription = "delivery",
                    modifier = Modifier.size(20.dp),
                    tint = Orange
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "Бесплатно",
                    fontSize = 14.sp,
                    color = IconGrey3
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = "delivery time",
                    modifier = Modifier.size(20.dp),
                    tint = Orange
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = "${restaurant.deliveryTimeMinutes} мин",
                    fontSize = 14.sp,
                    color = IconGrey3
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = restaurant.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = IconGrey3
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = restaurant.description,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            color = PlaceholderGrey
        )

        Spacer(modifier = Modifier.height(29.dp))

        if (uiState.value.categories.isNotEmpty()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.value.categories) { category ->
                    CategoryButton(
                        text = category,
                        isSelected = uiState.value.selectedCategory == category,
                        onClick = {
                            viewModel.selectCategory(category)
                            println("нажали")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = uiState.value.selectedCategory ?: "Категория не указана",
                fontSize = 20.sp,
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Категории отсутствуют",
                fontSize = 16.sp,
                color = PlaceholderGrey
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(21.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Фильтруем блюда по выбранной категории, если она выбрана
            val dishesToShow = uiState.value.selectedCategory?.let { category ->
                uiState.value.dishes.filter { it.category == category }
            } ?: uiState.value.dishes


            items(dishesToShow.size) { i ->
                val dish = dishesToShow[i]
                DishCard(
                    name = dish.name,
                    price = "$${dish.basePrice}", // форматируем цену
                    icon = dish.photoUrl ?: "https://yastatic.net/naydex/yandex-search/b1sNx6865/ea576csEb/zpEWAUjQ0uvJh4njjmjZwqLAKiVOM57P3VdVY2NLN5HPCKpPBd-qkJdMAfG_IcLz-eUI2tK-rO34wARthPf1f8LZAkR5zdaesNKRgt5I1daqtV8pCkL23qk-XBIfDkrx4wi2qp1TNgE6sZQ0Z4g_9qXMWMf-06HoTCw",
                    onClick = { onDishDetail(dish.id) } // передаем id блюда в навигацию
                )
            }
        }

    }
}









