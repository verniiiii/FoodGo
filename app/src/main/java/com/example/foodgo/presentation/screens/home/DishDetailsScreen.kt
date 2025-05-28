package com.example.foodgo.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.dish.FullDishDTO
import com.example.foodgo.data.remote.dto.restaurant.RestaurantInfoDTO
import com.example.foodgo.presentation.viewmodel.DishDetailsViewModel

@Composable
fun DishDetailsScreen(
    dishId: Int,
    onBack: () -> Unit,
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
        CircularProgressIndicator(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize())
    } else if (error.value != null) {
        Text(text = error.value ?: stringResource(R.string.unknown_error), color = Color.Red)
    } else if (dish.value != null && restaurantInfo.value != null) {
        DishDetailsContent(dish = dish.value!!, restaurantInfo.value!!,
            selectedSizeLabel = selectedSizeLabel.value,
            onSizeSelected = dishDetailsViewModel::onSizeSelected, onBack = onBack)
    }else {
        Text(text = stringResource(R.string.no_data), color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun DishDetailsContent(
    dish: FullDishDTO,
    restaurantInfo: RestaurantInfoDTO,
    selectedSizeLabel: String?,
    viewModel: DishDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSizeSelected: (String) -> Unit
) {
    var count = viewModel.count.collectAsState()
    var currentPrice = viewModel.currentPrice.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()

    val showToast = viewModel.showOrderSuccessToast.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(showToast.value) {
        if (showToast.value) {
            Toast.makeText(context, context.getString(R.string.dish_add_to_cart), Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .height(321.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        AsyncImage(
            model = dish.photoUrl ,
            contentDescription = "Dish Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick =  onBack ,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )
        }

        IconButton(
            onClick = { viewModel.toggleFavorite(dish.id) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(MaterialTheme.colorScheme.onPrimary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favorite),
                contentDescription = stringResource(R.string.favorite),
                tint = if (isFavorite.value) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
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
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = dish.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color =MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(7.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.rest),
                contentDescription = stringResource(R.string.restaurant_image),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(11.dp))

            Text(
                text = restaurantInfo.name,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
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
                    contentDescription = stringResource(R.string.rating),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = dish.rating.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.delivery),
                    contentDescription = stringResource(R.string.delivery),
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
                    text = stringResource(
                        R.string.delivery_time_min,
                        restaurantInfo.deliveryTimeMinutes
                    ),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(19.dp))

        Text(
            text = dish.description,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.surface
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.size_without),
                color = MaterialTheme.colorScheme.surface,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 14.dp)
            )

            dish.sizes.forEach { size ->
                val isSelected = size.sizeLabel == selectedSizeLabel
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onSizeSelected(size.sizeLabel) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${size.sizeLabel}\"",
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.ingredients),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (dish.ingredients.isEmpty()) {
            Text(
                text = stringResource(R.string.ingredients_no),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.surface,
                fontStyle = FontStyle.Italic,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = dish.ingredients.joinToString(", "),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.surface,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

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
                .background(MaterialTheme.colorScheme.background)
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
                        text = "$${currentPrice.value}",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(MaterialTheme.colorScheme.onTertiary)
                            .padding(start = 14.dp, end = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    MaterialTheme.colorScheme.onSecondaryContainer,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { viewModel.min() },
                                modifier = Modifier.size(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.minus),
                                    contentDescription = stringResource(R.string.minus),
                                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = count.value.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    MaterialTheme.colorScheme.onSecondaryContainer,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { viewModel.pl() },
                                modifier = Modifier.size(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus),
                                    contentDescription = stringResource(R.string.plus),
                                    tint = MaterialTheme.colorScheme.inverseOnSurface,
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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_cart),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

