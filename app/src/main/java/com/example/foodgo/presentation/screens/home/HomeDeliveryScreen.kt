package com.example.foodgo.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodgo.R
import com.example.foodgo.presentation.components.FilterDialog
import com.example.foodgo.ui.theme.Black
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.ProfGrey
import com.example.foodgo.ui.theme.White
import com.example.foodgo.ui.theme.Yeylow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDeliveryScreen(navController: NavHostController) {
    var notificationCount = 2
    val restaurantList = listOf(
        Restaurant(
            name = "Ресторан В Розовом Саду",
            description = "Maecenas sed diam eget risus varius blandit sit amet non magna.",
            rating = 4.7f,
            deliveryTime = "20 мин",
            deliveryFee = "Бесплатно",
            image = painterResource(id = R.drawable.ic_launcher_background),
            categories = "Бургер - Курица - Рис - Крылышки"
        ),
        Restaurant(
            name = "Ужин на закате",
            description = "Integer posuere erat a ante venenatis dapibus posuere velit aliquet.",
            rating = 4.2f,
            deliveryTime = "25 мин",
            deliveryFee = "₽50",
            image = painterResource(id = R.drawable.ic_launcher_background),
            categories = "Паста - Пицца - Салаты"
        ),
        Restaurant(
            name = "Кафе \"Океанский бриз\"",
            description = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque.",
            rating = 4.5f,
            deliveryTime = "30 мин",
            deliveryFee = "₽75",
            image = painterResource(id = R.drawable.ic_launcher_background),
            categories = "Морепродукты - Гриль"
        )
    )
    val categories = listOf(
        Category("Всё", painterResource(id = R.drawable.ic_launcher_background), true),
        Category("Хот-дог", painterResource(id = R.drawable.ic_launcher_background), false),
        Category("Бургер", painterResource(id = R.drawable.ic_launcher_background), false)
    )

    val isDialogOpen = remember { mutableStateOf(false) }

    if (isDialogOpen.value) {
        FilterDialog(
            isDialogOpen = isDialogOpen,
            onApplyFilters = { offer, time, pricing, rating ->
                println("Filters applied: $offer, $time, $pricing, $rating")
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 50.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Header Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(49.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(45.dp)
                    .background(GreyLight, shape = CircleShape)
                    .clickable { navController.navigate("profile") }
            ) {
                Icon(
                    painter = painterResource(R.drawable.menu_icon),
                    contentDescription = "Menu",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ДОСТАВИТЬ В",
                    color = Orange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(3.dp))
                Row(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "РТУ МИРЭА",
                        color = PlaceholderGrey,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(R.drawable.poligon_bottom),
                        contentDescription = "Menu",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(45.dp)
                    .background(Color(0xFF181C2E), shape = CircleShape)
                    .clickable { navController.navigate("cart") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.basket_icon),
                    tint = White,
                    contentDescription = "Cart",
                    modifier = Modifier.size(24.dp)
                )

                if (notificationCount > 0) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (0).dp, y = (-2.5).dp)
                            .background(Orange, shape = CircleShape)
                    ) {
                        Text(
                            text = notificationCount.toString(),
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(
                text = "Вероника, ",
                color = Black,
                fontSize = 16.sp
            )
            Text(
                text = "Добрый День!",
                color = Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Section
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().height(62.dp),
            placeholder = {
                Text(
                    text = "Поиск блюд, ресторанов",
                    fontSize = 14.sp,
                    color = PlaceholderGrey,
                    modifier = Modifier.fillMaxHeight(),
                    textAlign = TextAlign.Start,
                    lineHeight = 31.sp,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = GreyLight
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(20.dp).clickable {
                        isDialogOpen.value = true
                    },
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(10.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Все Категории",
                color = ProfGrey,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Всё",
                color = ProfGrey,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                modifier = Modifier.size(6.dp, 12.dp),
                painter = painterResource(id = R.drawable.right),
                contentDescription = "Search",
                tint = PlaceholderGrey
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Categories Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(categories) { category ->
                FoodDeliveryCategoryButton(
                    text = category.text,
                    isSelected = category.isSelected,
                    icon = category.icon
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Открытые Рестораны",
                color = ProfGrey,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Всё",
                color = ProfGrey,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                modifier = Modifier.size(6.dp, 12.dp),
                painter = painterResource(id = R.drawable.right),
                contentDescription = "Search",
                tint = PlaceholderGrey
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Restaurant Cards
        LazyColumn {
            items(restaurantList) { restaurant ->
                OpenRestaurantColumn(
                    name = restaurant.name,
                    categories = restaurant.categories,
                    rating = restaurant.rating,
                    deliveryTime = restaurant.deliveryTime,
                    deliveryFee = restaurant.deliveryFee,
                    onClick = {
                        navController.navigate("restaurantDetails")
                    }
                )
            }
        }
    }
}


@Composable
fun FoodDeliveryCategoryButton(text: String, isSelected: Boolean, icon: Painter) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(39.dp),
        modifier = Modifier
            .height(60.dp)
            .shadow(8.dp, RoundedCornerShape(39.dp))
            .clip(RoundedCornerShape(39.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Yeylow else White,
            contentColor = ProfGrey
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        // Добавляем изображение и текст
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp) // Размер круга
                    .clip(CircleShape) // Обрезаем изображение в круг
                    .background(Color.Gray), // Цвет фона для круга
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize() // Заполняет круг изображением
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ProfGrey)
            Spacer(modifier = Modifier.width(5.dp))

        }
    }
}



@Composable
fun OpenRestaurantColumn(
    name: String,
    categories: String,
    rating: Float,
    deliveryTime: String,
    deliveryFee: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp)
            .background(White)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Restaurant Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray),
            contentScale = ContentScale.Crop
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


//@Preview(showBackground = true)
//@Composable
//fun HomeDeliveryScreenPreview() {
//    HomeDeliveryScreen()
//}


// Модель данных для категории
data class Category(
    val text: String,
    val icon: Painter,
    val isSelected: Boolean
)