package com.example.foodgo.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO
import com.example.foodgo.presentation.components.restaurants.CategoryButton
import com.example.foodgo.presentation.components.restaurants.DishCard
import com.example.foodgo.presentation.viewmodel.restaurants.RestaurantDetailsViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun RestaurantDetailsScreen(
    restaurant: RestaurantWithPhotosDTO,
    onDishDetail: (dish_id: Int) -> Unit,
    onBack: () -> Unit,
    viewModel: RestaurantDetailsViewModel = hiltViewModel(),
) {
    val pagerState = rememberPagerState()
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadRestaurantData(restaurant, context)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(321.dp)
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        HorizontalPager(
            count = uiState.value.imageUrls.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = uiState.value.imageUrls[page],
                contentDescription = stringResource(R.string.restaurant_image),
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
                            .background(if (index == pagerState.currentPage) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface)
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
                .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(top = 321.dp)
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(start = 24.dp, end = 24.dp)

    ) {
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.star1),
                    contentDescription = stringResource(R.string.rating),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = restaurant.rating.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.delivery),
                    contentDescription = stringResource(R.string.rating),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = stringResource(R.string.free),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = stringResource(R.string.time_delivery),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = stringResource(R.string.delivery_time_min, restaurant.deliveryTimeMinutes),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = restaurant.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = restaurant.description,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.surface
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
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = uiState.value.selectedCategory ?: stringResource(R.string.categoty_no),
                fontSize = 20.sp,
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.category_no2),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(21.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            val dishesToShow = uiState.value.selectedCategory?.let { category ->
                uiState.value.dishes.filter { it.category == category }
            } ?: uiState.value.dishes

            items(dishesToShow.size) { i ->
                val dish = dishesToShow[i]
                DishCard(
                    name = dish.name,
                    price = "$${dish.basePrice}",
                    icon = dish.photoUrl ?: stringResource(R.string.dish_url_no),
                    onClick = { onDishDetail(dish.id) }
                )
            }
        }

    }
}









