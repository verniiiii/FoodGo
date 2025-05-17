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
import androidx.navigation.NavHostController
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@Composable
fun RestaurantDetailsScreen(navController: NavHostController) {
    val restaurant = Restaurant(
        name = "Пикантный ресторан",
        description = "Яркие блюда с острыми специями и экзотическими вкусами, сочетая восточную и средиземноморскую кухни. ",
        rating = 4.7f,
        deliveryFee = "Бесплатно",
        deliveryTime = "20 мин",
        image = painterResource(id = R.drawable.ic_launcher_background),
        categories = "Бургер - Сэндвич - Пицца - Суши"
    )

    val images = listOf(
        painterResource(id = R.drawable.ic_launcher_background),
        painterResource(id = R.drawable.ic_launcher_background),
        painterResource(id = R.drawable.ic_launcher_background),
        painterResource(id = R.drawable.ic_launcher_background),
        painterResource(id = R.drawable.ic_launcher_background)
    )

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(321.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = images[page],
                contentDescription = "Restaurant Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 7.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) White else PlaceholderGrey
                        )
                )
            }
        }

        IconButton(
            onClick = { navController.popBackStack() },
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

        IconButton(
            onClick = { /* Logic for favorite action */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "More",
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
                    text = restaurant.deliveryFee,
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
                    text = restaurant.deliveryTime,
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
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = restaurant.categories.split(" - ")
            items(categories) { category ->
                val isSelected = categories.indexOf(category) == 0
                CategoryButton(
                    text = category,
                    isSelected = isSelected,
                    icon = painterResource(id = R.drawable.ic_launcher_background)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "${restaurant.categories.split(" - ")[0]} (10)",
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(21.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(10) { index ->
                DishCard(
                    name = "Бургер Фергюсон",
                    price = "$40",
                    restaurantName = restaurant.name,
                    onClick = { navController.navigate("dishDetails") }
                )
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, isSelected: Boolean, icon: Painter) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(33.dp),
        modifier = Modifier
            .height(46.dp)
            .then(
                // Добавляем обводку для неактивных элементов
                if (!isSelected) {
                    Modifier.border(2.dp, GreyLight, RoundedCornerShape(33.dp))
                } else {
                    Modifier
                }
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LiteOrange else White,
            contentColor = if (isSelected) White else IconGrey3 // Меняем цвет текста на черный, если неактивная кнопка
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal // Убираем жирное начертание
        )
    }
}




@Composable
fun DishCard(name: String, price: String, restaurantName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(153.dp)
            .height(174.dp)
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
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Dish Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 9.dp, end = 8.dp)
                    .height(75.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = restaurantName,
                fontSize = 13.sp,
                color = PlaceholderGrey
            )
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

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(LiteOrange)
                        .clickable { /* Обработка нажатия */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+", color = White, fontSize = 20.sp)
                }
            }
        }
    }
}

data class Restaurant(
    val name: String,
    val description: String,
    val rating: Float,
    val deliveryFee: String,
    val deliveryTime: String,
    val image: Painter,
    val categories: String
)

//@Preview(showBackground = true)
//@Composable
//fun RestaurantDetailsScreenPreview() {
//    RestaurantDetailsScreen()
//}

