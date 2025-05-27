package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO

@Composable
fun RestaurantsSection(
    filteredRestaurants: List<RestaurantWithPhotosDTO>,
    onRestaurant: (rest: RestaurantWithPhotosDTO) -> Unit
) {
    Text(
        text = stringResource(R.string.restaurant_section),
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    if (filteredRestaurants.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = stringResource(R.string.no_restaurants),
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.no_restaurants),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    LazyColumn {
        items(filteredRestaurants) { restaurant ->
            OpenRestaurantColumn(
                name = restaurant.name,
                categories = restaurant.categories.joinToString(" - "),
                rating = restaurant.rating,
                deliveryTime = restaurant.deliveryTimeMinutes.toString() + " мин",
                deliveryFee = stringResource(R.string.free),
                restaurantImageUrl =
                    if(restaurant.photos.isNotEmpty()) restaurant.photos[0]
                    else stringResource(R.string.url_no_open_rest),
                onClick = {
                    onRestaurant(restaurant)
                }
            )
        }
    }
}
