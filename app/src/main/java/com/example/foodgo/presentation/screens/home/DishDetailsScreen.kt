package com.example.foodgo.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.foodgo.R
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
fun DishDetailsScreen() {
    val dish = Dish(
        name = "Бургер Фергюсон",
        description = "Быстро, вкусно и сытно — идеальный выбор для перекуса или обеда в непринужденной атмосфере.",
        rating = 4.7f,
        price = "$32",
        deliveryFee = "Бесплатно",
        deliveryTime = "30 мин",
        image = painterResource(id = R.drawable.ic_launcher_background), // Replace with actual image resource
    )
    val rest = "Пикантный Ресторан"
    val selectedSize = "14" // This can be dynamic based on user selection

    // Slider setup
    val images = listOf(
        painterResource(id = R.drawable.ic_launcher_background),
        painterResource(id = R.drawable.ic_launcher_background),
        painterResource(id = R.drawable.ic_launcher_background)
    )

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(321.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)) // Rounded bottom corners
    ) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize() // Ensure the pager fills the available space
        ) { page ->
            Image(
                painter = images[page],
                contentDescription = "Dish Image",
                modifier = Modifier.fillMaxSize(), // Image should fill the entire space
                contentScale = ContentScale.Crop
            )
        }

        // Back button in the top left corner
        IconButton(
            onClick = { /* Logic for returning to the previous screen */ },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back), // Replace with your icon
                contentDescription = "Back",
                tint = Color(0xFF181C2E),
                modifier = Modifier.size(18.dp)
            )
        }

        // Favorite button in the top right corner
        IconButton(
            onClick = { /* Logic for favorite action */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp, top = 50.dp)
                .size(45.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favorite), // Replace with your icon
                contentDescription = "Favorite",
                tint = LiteOrange,
                modifier = Modifier.size(18.dp)
            )
        }
    }

    // Now, everything inside Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 321.dp, start = 24.dp, end = 24.dp) // Padding for the content after the image carousel
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
                text = rest,
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
                    text = dish.deliveryFee,
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
                    text = dish.deliveryTime,
                    fontSize = 14.sp,
                    color = Color(0xFF181C2E)
                )
            }
        }
        Spacer(modifier = Modifier.height(19.dp))

        Text(
            text = dish.description,
            fontSize = 14.sp,
            lineHeight = 24.sp,  // Set line height
            color = PlaceholderGrey
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Size label
            Text(
                text = "РАЗМЕР:",
                color = PlaceholderGrey,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 14.dp)
            )

            // Size options
            listOf("10", "14", "16").forEach { size ->
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = if (size == selectedSize) LiteOrange else GreyLight,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$size\"",
                        color = if (size == selectedSize) Color.White else DarkBlack,
                        fontSize = 16.sp,
                        fontWeight = if (size == selectedSize) FontWeight.Bold else FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Ingredients section
        Text(
            text = "ИНГРЕДИЕНТЫ:",
            fontSize = 13.sp,
            color = ProfGrey
        )
        Spacer(modifier = Modifier.height(20.dp))

        // List of vegetable icons
        val ingredients = listOf(
            Ingredient(R.drawable.salt, "Соль"),
            Ingredient(R.drawable.chicken, "Курица"),
            Ingredient(R.drawable.onion, "Лук\n(Алерг)"),
            Ingredient(R.drawable.garlic, "Чеснок"),
            Ingredient(R.drawable.pappers, "Перец\n(Алерг)"),
            Ingredient(R.drawable.ginger, "Имбирь"),
            Ingredient(R.drawable.broccoli, "Брокколи"),
            Ingredient(R.drawable.orange, "Апельсин"),
            Ingredient(R.drawable.walnut, "Орехи")
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Grouping the icons into rows, with a maximum of 5 per row
            ingredients.chunked(5).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { ingredient ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier

                        ) {
                            // Icon inside the circle
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(LiteOrange3, shape = CircleShape)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = ingredient.icon),
                                    contentDescription = ingredient.name,
                                    modifier = Modifier.size(24.dp),
                                    tint = Orange
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            // Text below the icon
                            Text(
                                text = ingredient.name,
                                fontSize = 12.sp,
                                color = PlaceholderGrey,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize() // Заполняем весь экран
    ) {
        // Нижний блок с ценой, счётчиком и кнопкой
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Прикрепляем к низу экрана
                .fillMaxWidth() // Заполняем всю ширину
                .height(178.dp) // Указываем фиксированную высоту
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // Увеличенный радиус закругления верхних углов
                .background(GreyLight) // Фон для Box
                .padding(start = 24.dp, end = 24.dp, top = 20.dp) // Padding внутри Box
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Верхняя строка: цена и счетчик
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                    // Цена слева
                    Text(
                        text = "$32",
                        fontSize = 28.sp,
                        color = Color(0xFF181C2E)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(50.dp)) // Закругленные углы
                            .background(DarkBlack)
                            .padding(start = 14.dp, end = 14.dp), // Добавляем фон для видимости закругленных углов
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Кнопка "-"
                        Box(
                            modifier = Modifier
                                .size(24.dp)

                                .background(IconGrey6, shape = CircleShape), // Кнопка с закругленными углами
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { /* Логика уменьшения */ },
                                modifier = Modifier.size(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.minus), // Замените на свой иконку
                                    contentDescription = "Decrease",
                                    tint = White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Число
                        Text(
                            text = "2", // Число (счётчик)
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )

                        // Кнопка "+"
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(IconGrey6, shape = CircleShape)
                               , // Кнопка с закругленными углами
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { /* Логика увеличения */ },
                                modifier = Modifier.size(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus), // Замените на свой иконку
                                    contentDescription = "Increase",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                // Кнопка "Add to Cart"
                Button(
                    onClick = { /* Логика добавления в корзину */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange), // Цвет фона кнопки
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


data class Dish(
    val name: String,
    val description: String,
    val rating: Float,
    val price: String,
    val deliveryFee: String,
    val deliveryTime: String,
    val image: Painter
)

data class Ingredient(val icon: Int, val name: String)

@Preview(showBackground = true)
@Composable
fun DishDetailsScreenPreview() {
    DishDetailsScreen()
}
