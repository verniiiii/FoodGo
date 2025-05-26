package com.example.foodgo.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.order.CartDishDTO
import com.example.foodgo.presentation.components.CartItem
import com.example.foodgo.presentation.viewmodel.BasketViewModel
import com.example.foodgo.presentation.viewmodel.UserViewModel
import com.example.foodgo.ui.theme.Green
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: BasketViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val name = remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }
    val cartItems = remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    val dishes = remember { mutableStateOf<List<CartDishDTO>>(emptyList()) }
    val userAddresses = userViewModel.userAddresses.collectAsState()
    val expanded = remember { mutableStateOf(false) }

    LaunchedEffect(userAddresses.value) {
        if (userAddresses.value.isNotEmpty()) {
            name.value = userAddresses.value.first().city + ", " + userAddresses.value.first().addressLine
        } else {
            name.value = "Добавьте адрес для доставки в профиле"
        }
    }

    LaunchedEffect(Unit) {
        cartItems.value = viewModel.getCartItems()
        dishes.value = viewModel.getCartDishes()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121223))
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
                    .size(45.dp)
                    .background(IconGrey3, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = "Корзина",
                color = White,
                fontSize = 17.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (isEditing.value) "ГОТОВО" else "РЕДАКТИРОВАТЬ",
                color = if (isEditing.value) Green else Orange,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.Underline
                ),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    isEditing.value = !isEditing.value
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cart items
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // занимает всю высоту
                .padding(bottom = 300.dp) // оставляет снизу 300.dp,

        ) {
            if (dishes.value.isEmpty()) {
                item {
                    Text(
                        text = "Корзина пуста",
                        color = White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(dishes.value.size) { i ->
                    val dish = dishes.value[i]
                    val key = if (dish.size != null) "${dish.id}|${dish.size}" else "${dish.id}"
                    val quantity = cartItems.value[key] ?: 0
                    if (quantity > 0) {
                        CartItem(
                            dish = dish,
                            quantity = quantity,
                            isEditing = isEditing.value,
                            onQuantityChanged = { newQuantity ->
                                val updatedCart = cartItems.value.toMutableMap()
                                if (newQuantity > 0) {
                                    updatedCart[key] = newQuantity
                                } else {
                                    updatedCart.remove(key)
                                }
                                viewModel.saveCartItems(updatedCart)
                                cartItems.value = updatedCart
                            },
                            onRemove = {
                                val updatedCart = cartItems.value.toMutableMap()
                                updatedCart.remove(key)
                                viewModel.saveCartItems(updatedCart)
                                cartItems.value = updatedCart
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

    }

    // Calculate total price
    val totalPrice = remember(cartItems.value, dishes.value) {
        dishes.value.sumOf { dish ->
            val key = if(dish.size != null) "${dish.id}|${dish.size}" else "${dish.id}"
            (cartItems.value[key] ?: 0) * dish.sizePrice
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Bottom panel with price and checkout button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(310.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(White)
                .padding(start = 24.dp, end = 24.dp, top = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row {
                    Text(
                        text = "АДРЕС ДОСТАВКИ",
                        color = PlaceholderGrey,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "РЕДАКТИРОВАТЬ",
                        color = Orange,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            expanded.value = !expanded.value
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Box {
                    TextField(
                        value = name.value,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("Москва, пр-кт Вернадского 86", fontSize = 14.sp, color = PlaceholderGrey) },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(62.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            containerColor = GreyLight
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        modifier = Modifier.fillMaxWidth().background(White)
                    ) {
                        if (userAddresses.value.isNotEmpty()) {
                            userAddresses.value.forEach { address ->
                                DropdownMenuItem(
                                    text = { Text(address.city + ", " + address.addressLine) },
                                    onClick = {
                                        name.value = address.city + ", " + address.addressLine
                                        expanded.value = false
                                    }
                                )
                            }
                        } else {
                            DropdownMenuItem(
                                text = { Text("Пожалуйста, добавьте адрес для доставки в профиле") },
                                onClick = {
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }

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
                        text = "$$totalPrice",
                        fontSize = 30.sp,
                        color = Color(0xFF181C2E)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                // Checkout button
                Button(
                    onClick = {
                        if(totalPrice != 0.0) {
                            viewModel.createOrder(
                                dishes = dishes.value,
                                quantities = cartItems.value,
                                address = name.value
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
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




