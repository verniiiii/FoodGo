package com.example.foodgo.presentation.components.restaurants

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.PreferencesManager
import com.example.foodgo.R

import com.example.foodgo.presentation.viewmodel.DishDetailsViewModel
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White

@Composable
fun DishCard(id: Int, name: String, price: String, icon: String,
             dishDetailsViewModel: DishDetailsViewModel = hiltViewModel(),

             onClick: () -> Unit) {


    Card(
        modifier = Modifier
            .width(153.dp)
            //.height(174.dp)
            .wrapContentHeight()
            .background(GreyLight)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = White),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = icon,
                contentDescription = "Dish Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 9.dp, end = 8.dp)
                    .height(75.dp)
                    .clip(RoundedCornerShape(15.dp)),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    fontSize = 16.sp,
                    color = PlaceholderGrey,
                    fontWeight = FontWeight.Bold
                )




            }
        }
    }
}
