package com.example.foodgo.presentation.components.home

import android.net.Uri
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO
import com.example.foodgo.presentation.navigation.Destination
import com.example.foodgo.ui.theme.ProfGrey
import com.google.gson.Gson

@Composable
fun RestaurantsSection(
    filteredRestaurants: List<RestaurantWithPhotosDTO>,
    onRestaurant: (rest: RestaurantWithPhotosDTO) -> Unit
) {
    Text(
        text = stringResource(R.string.restaurant_section),
        color = ProfGrey,
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
                    contentDescription = "Нет ресторанов",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Нет доступных ресторанов",
                    color = ProfGrey,
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
                deliveryFee = "Бесплатно",
                restaurantImageUrl = if(restaurant.photos.isNotEmpty()) restaurant.photos[0] else "https://yastatic.net/naydex/yandex-search/b1sNx6865/ea576csEb/zpEWAUjQ0uvJh4njjmjZwqLAKiVOM57P3VdVY2NLN5HPCKpPBd-qkJdMAfG_IcLz-eUI2tK-rO34wARthPf1f8LZAkR5zdaesNKRgt5I1daqtV8pCkL23qk-XBIfDkrx4wi2qp1TNgE6sZQ0Z4g_9qXMWMf-06HoTCw",
                onClick = {
                    onRestaurant(restaurant)
                }
            )
        }
    }
}
