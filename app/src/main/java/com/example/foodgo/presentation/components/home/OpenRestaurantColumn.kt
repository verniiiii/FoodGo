package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodgo.R
import com.example.foodgo.ui.theme.Black
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White

@Composable
fun OpenRestaurantColumn(
    name: String,
    categories: String,
    rating: Double,
    deliveryTime: String,
    deliveryFee: String,
    restaurantImageUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp)
            .background(White)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = restaurantImageUrl,
            contentDescription = "Restaurant Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_launcher_background),
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            fontSize = 20.sp,
            color = Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = categories,
            fontSize = 14.sp,
            color = PlaceholderGrey
        )
        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
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
                    text = "$rating",
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
                    text = deliveryFee,
                    fontSize = 14.sp,
                    color = IconGrey3
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = "delivery",
                    modifier = Modifier.size(20.dp),
                    tint = Orange
                )
                Spacer(modifier = Modifier.width(9.dp))

                Text(
                    text = deliveryTime,
                    fontSize = 14.sp,
                    color = IconGrey3
                )
            }
        }
    }
}