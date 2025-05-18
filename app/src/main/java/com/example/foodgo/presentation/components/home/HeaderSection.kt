package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.UserAddressDTO
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White

@Composable
fun HeaderSection(
    navController: NavHostController,
    notificationCount: Int,
    selectedAddress: MutableState<String>,
    expanded: MutableState<Boolean>,
    addresses: List<UserAddressDTO>
) {
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
                    text = selectedAddress.value.ifEmpty { "Адрес не выбран" },
                    color = PlaceholderGrey,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    Icon(
                        painter = painterResource(R.drawable.poligon_bottom),
                        contentDescription = "Выбор адреса",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { expanded.value = !expanded.value }
                    )
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        addresses.forEach { addressDTO ->
                            DropdownMenuItem(
                                text = { Text(addressDTO.addressLine) },
                                onClick = {
                                    selectedAddress.value = addressDTO.addressLine
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
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
}