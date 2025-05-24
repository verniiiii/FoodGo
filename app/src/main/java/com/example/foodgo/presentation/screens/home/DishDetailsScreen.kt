package com.example.foodgo.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.R
import com.example.foodgo.data.remote.api.DishApi
import com.example.foodgo.presentation.viewmodel.DishDetailsViewModel
import com.example.foodgo.presentation.viewmodel.RestaurantInfoDTO
import com.example.foodgo.ui.theme.DarkBlack
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey6
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.LiteOrange3
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.ProfGrey
import com.example.foodgo.ui.theme.White
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun DishDetailsScreen(
    dishId: Int,
    dishDetailsViewModel: DishDetailsViewModel = hiltViewModel()
) {
    val dish = dishDetailsViewModel.dishState.collectAsState()
    val restaurantInfo = dishDetailsViewModel.restaurantInfo.collectAsState()
    val isLoading = dishDetailsViewModel.isLoading.collectAsState()
    val error = dishDetailsViewModel.error.collectAsState()
    val selectedSizeLabel = dishDetailsViewModel.selectedSizeLabel.collectAsState()


    LaunchedEffect(dishId) {
        dishDetailsViewModel.loadDish(dishId)
    }

    if (isLoading.value) {
        // Show loading indicator
        CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
    } else if (error.value != null) {
        // Show error message
        Text(text = error.value ?: "Unknown error", color = Color.Red)
    } else if (dish.value != null && restaurantInfo.value != null) {
        DishDetailsContent(dish = dish.value!!, restaurantInfo.value!!,
            selectedSizeLabel = selectedSizeLabel.value,
            onSizeSelected = dishDetailsViewModel::onSizeSelected)
    }else {
        Text(text = "Нет доступных данных", color = Color.Black)
    }
}

@Composable
fun DishDetailsContent(
    dish: FullDishDTO,
    restaurantInfo: RestaurantInfoDTO,
    selectedSizeLabel: String?,
    viewModel: DishDetailsViewModel = hiltViewModel(),
    onSizeSelected: (String) -> Unit
) {
    var count = viewModel.count.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()

    Box(
        modifier = Modifier
            .height(321.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        AsyncImage(
            model = dish.photoUrl , // Use the photo URL from your dish data
            contentDescription = "Dish Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { /* Logic for returning to the previous screen */ },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color(0xFF181C2E),
                modifier = Modifier.size(18.dp)
            )
        }

        IconButton(
            onClick = { viewModel.toggleFavorite(dish.id) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favorite),
                contentDescription = "Favorite",
                tint = if (isFavorite.value) LiteOrange else GreyLight,  // цвет зависит от isFavorite
                modifier = Modifier.size(18.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 321.dp, start = 24.dp, end = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = dish.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF181C2E)
        )

        Spacer(modifier = Modifier.height(7.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.rest),
                contentDescription = "rest",
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(11.dp))

            Text(
                text = restaurantInfo.name, // Replace with actual restaurant name
                fontSize = 14.sp,
                color = Color(0xFF181C2E)
            )
        }

        Spacer(modifier = Modifier.height(21.dp))

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
                    text = dish.rating.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF181C2E)
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
                    text = "Бесплатно", // Replace with actual delivery fee
                    fontSize = 14.sp,
                    color = Color(0xFF181C2E)
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
                    text = "${restaurantInfo.deliveryTimeMinutes} мин", // Replace with actual delivery time
                    fontSize = 14.sp,
                    color = Color(0xFF181C2E)
                )
            }
        }
        Spacer(modifier = Modifier.height(19.dp))

        Text(
            text = dish.description,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            color = PlaceholderGrey
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "РАЗМЕР:",
                color = PlaceholderGrey,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 14.dp)
            )

            dish.sizes.forEach { size ->
                val isSelected = size.sizeLabel == selectedSizeLabel
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(
                            color = if (isSelected) LiteOrange else GreyLight,
                            shape = CircleShape
                        )
                        .clickable(
                            indication = null, // отключает ripple/эффект нажатия
                            interactionSource = remember { MutableInteractionSource() } // отключает фокус
                        ) { onSizeSelected(size.sizeLabel) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${size.sizeLabel}\"",
                        color = if (isSelected) Color.White else DarkBlack,
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "ИНГРЕДИЕНТЫ:",
            fontSize = 13.sp,
            color = ProfGrey
        )
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = dish.ingredients.joinToString(", "),
            fontSize = 14.sp,
            color = PlaceholderGrey,
            lineHeight = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(178.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(GreyLight)
                .padding(start = 24.dp, end = 24.dp, top = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "$${dish.basePrice}",
                        fontSize = 28.sp,
                        color = Color(0xFF181C2E)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(DarkBlack)
                            .padding(start = 14.dp, end = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(IconGrey6, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { viewModel.min() },
                                modifier = Modifier.size(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.minus),
                                    contentDescription = "Decrease",
                                    tint = White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = count.value.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(IconGrey6, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { viewModel.pl() },
                                modifier = Modifier.size(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus),
                                    contentDescription = "Increase",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { viewModel.addToCart() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "В КОРЗИНУ",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}



data class FullDishDTO(
    val id: Int,
    val restaurantId: Int,
    val name: String,
    val basePrice: Double,
    val description: String,
    val rating: Double,
    val category: String?,
    val photoUrl: String,
    val sizes: List<DishSizeDTO>,
    val ingredients: List<String>
)

data class DishSizeDTO(
    val sizeLabel: String,
    val price: Double
)




