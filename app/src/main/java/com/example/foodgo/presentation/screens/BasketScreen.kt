package com.example.foodgo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.ui.theme.Green
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.ProfGrey
import com.example.foodgo.ui.theme.Red
import com.example.foodgo.ui.theme.White
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    val name = remember { mutableStateOf("") }
    // New state to toggle edit/done mode
    val isEditing = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121223)) // Dark background color
            .padding(start = 24.dp, end = 24.dp, top = 50.dp)
    ) {
        // Top bar with "Cart" and edit button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp) // Set the size of the circle
                    .background(IconGrey3, shape = CircleShape) // Set the background color and shape
                ,
                contentAlignment = Alignment.Center // Center the icon within the circle
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(18.dp) // Adjust the size of the icon as needed
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = "Корзина",
                color = White, // Orange color
                fontSize = 17.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (isEditing.value) "ГОТОВО" else "РЕДАКТИРОВАТЬ", // Toggle between "EDIT ITEMS" and "DONE"
                color = if (isEditing.value) Green else Orange, // Orange color
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.Underline
                ),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    // Toggle the edit mode when clicked
                    isEditing.value = !isEditing.value
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cart items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            CartItem("Pizza Calzone\nEuropean", 64, 2, isEditing.value)
            Spacer(modifier = Modifier.height(8.dp))
            CartItem("Pizza Calzone\nEuropean", 32, 1, isEditing.value)
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
                .height(310.dp) // Указываем фиксированную высоту
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // Увеличенный радиус закругления верхних углов
                .background(White) // Фон для Box
                .padding(start = 24.dp, end = 24.dp, top = 20.dp) // Padding внутри Box
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row {
                    Text(
                        text = "АДРЕС ДОСТАВКИ",
                        color = PlaceholderGrey, // Orange color
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "РЕДАКТИРОВАТЬ",
                        color = Orange, // Orange color
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    placeholder = { Text("Москва, пр-кт Вернадского 86", fontSize = 14.sp, color = PlaceholderGrey) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(62.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,  // Убираем обводку
                        unfocusedBorderColor = Color.Transparent, // Убираем обводку
                        containerColor = GreyLight  // Серый фон для поля
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "ВСЕГО:",
                        fontSize = 14.sp,
                        color = PlaceholderGrey
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "\$96",
                        fontSize = 30.sp,
                        color = Color(0xFF181C2E)
                    )


                }
                Spacer(modifier = Modifier.height(32.dp))

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
                        text = "СОЗДАТЬ ЗАКАЗ",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CartItem(name: String, price: Int, quantity: Int, isEditing: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
    ) {
        // Изображение с закругленными краями слева
        Box(
            modifier = Modifier
                .size(136.dp, 117.dp)
                .clip(RoundedCornerShape(25.dp)), // Закругленные края
            contentAlignment = Alignment.TopEnd // Align the delete icon at the top right
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Замените на ваш ресурс изображения
                contentDescription = null,
                modifier = Modifier
                    .size(136.dp, 117.dp) // Установите размер изображения
                    .clip(RoundedCornerShape(25.dp)), // Закругленные края
                contentScale = ContentScale.Crop // Масштабируем изображение, чтобы оно заполнило контейнер
            )


        }
        Spacer(modifier = Modifier.width(20.dp))
        // Колонка справа
        Column(
            modifier = Modifier.height(117.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Text(text = name, color = Color.White, fontSize = 18.sp)
                // Remove Icon (only visible in edit mode)
                Spacer(modifier = Modifier.weight(1f))

                if (isEditing) {
                    Box(
                        modifier = Modifier
                            .size(27.dp)
                            .clip(CircleShape)
                            .background(Red) // Red background
                            .clickable { /* Logic for removing the item */ },
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.krest), // Use your own delete icon
                            contentDescription = "Remove",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "\$$price", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))

            // Правая часть с ценой и кнопками
            Row(modifier = Modifier.height(22.dp), verticalAlignment = Alignment.Bottom) {
                Text(text = "14\"", color = PlaceholderGrey)
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(RoundedCornerShape(50.dp)) // Закругленные углы
                        .background(Color(0xFF121223))
                        .padding(start = 14.dp, end = 14.dp), // Добавляем фон для видимости закругленных углов
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Кнопка "-"
                    Box(
                        modifier = Modifier
                            .size(24.dp)

                            .background(ProfGrey, shape = CircleShape), // Кнопка с закругленными углами
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { /* Логика уменьшения */ },
                            modifier = Modifier.size(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus), // Замените на свой иконку
                                contentDescription = "Decrease",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Число
                    Text(
                        text = quantity.toString(), // Число (счётчик)
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Кнопка "+"
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(ProfGrey, shape = CircleShape)
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen()
}