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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.PreferencesManager
import com.example.foodgo.R
import com.example.foodgo.presentation.screens.home.FullDishDTO
import com.example.foodgo.presentation.viewmodel.BasketViewModel
import com.example.foodgo.presentation.viewmodel.UserViewModel
import com.example.foodgo.ui.theme.Green
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.ProfGrey
import com.example.foodgo.ui.theme.Red
import com.example.foodgo.ui.theme.White
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: BasketViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    preferencesManager: PreferencesManager
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
            name.value = "Пожалуйста, добавьте адрес для доставки в профиле"
        }
    }

    LaunchedEffect(Unit) {
        cartItems.value = preferencesManager.getCartItems()
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            if (dishes.value.isEmpty()) {
                Text(
                    text = "Корзина пуста",
                    color = White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                dishes.value.forEach { dish ->
                    val key = "${dish.id}|${dish.size}"
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
                                preferencesManager.saveCartItems(updatedCart)
                                cartItems.value = updatedCart
                            },
                            onRemove = {
                                val updatedCart = cartItems.value.toMutableMap()
                                updatedCart.remove(key)
                                preferencesManager.saveCartItems(updatedCart)
                                cartItems.value = updatedCart
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    // Calculate total price
    val totalPrice = remember(cartItems.value, dishes.value) {
        dishes.value.sumOf { dish ->
            val key = "${dish.id}|${dish.size}"
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


@Composable
fun CartItem(
    dish: CartDishDTO,
    quantity: Int,
    isEditing: Boolean,
    onQuantityChanged: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
    ) {
        // Dish image
        Box(
            modifier = Modifier
                .size(136.dp, 117.dp)
                .clip(RoundedCornerShape(25.dp)),
            contentAlignment = Alignment.TopEnd
        ) {
            // Load image using Coil or Glide
            AsyncImage(
                model = dish.photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(136.dp, 117.dp)
                    .clip(RoundedCornerShape(25.dp)),
                contentScale = ContentScale.Crop
            )


        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.height(117.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = dish.name, color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(27.dp)
                        .clip(CircleShape)
                        .background(if (isEditing) Red else Color.Transparent)
                        .clickable(enabled = isEditing) { if (isEditing) onRemove() }
                        .then(if (isEditing) Modifier else Modifier.alpha(0f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.krest),
                        contentDescription = "Remove",
                        tint = if (isEditing) Color.White else Color.Transparent,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }



            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "\$${dish.sizePrice}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.height(22.dp), verticalAlignment = Alignment.Bottom) {
                Text(text = dish.size ?: "", color = PlaceholderGrey)
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFF121223))
                        .padding(start = 14.dp, end = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Decrease button
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(ProfGrey, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { onQuantityChanged(quantity - 1) },
                            modifier = Modifier.size(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = "Decrease",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Quantity
                    Text(
                        text = quantity.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Increase button
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(ProfGrey, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { onQuantityChanged(quantity + 1) },
                            modifier = Modifier.size(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus),
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

data class CartDishDTO(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val size: String,
    val sizePrice: Double            // Цена для этого размера
)