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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.user.UserAddressDTO
import com.example.foodgo.presentation.viewmodel.BasketViewModel

@Composable
fun HeaderSection(
    onProfile: () -> Unit,
    onCart: () -> Unit,
    selectedAddress: MutableState<String>,
    expanded: MutableState<Boolean>,
    addresses: androidx.compose.runtime.State<List<UserAddressDTO>>,
    basketViewModel: BasketViewModel = hiltViewModel()
) {
    var notificationCount = basketViewModel.cartItemCount.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(54.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(45.dp)
                .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                .clickable { onProfile() }
        ) {
            Icon(
                painter = painterResource(R.drawable.menu_icon),
                contentDescription = stringResource(R.string.menu),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(18.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.delivery_in),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(3.dp))
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedAddress.value.ifEmpty { stringResource(R.string.no_addresses) },
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    Icon(
                        painter = painterResource(R.drawable.poligon_bottom),
                        contentDescription = stringResource(R.string.сhoos_address),
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { expanded.value = !expanded.value }
                    )
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        addresses.value.forEach { addressDTO ->
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
                .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                .clickable { onCart() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.basket_icon),
                contentDescription = "Cart",
                modifier = Modifier.size(24.dp)
            )

            if (notificationCount.value > 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (0).dp, y = (-2.5).dp)
                        .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                ) {
                    Text(
                        text = notificationCount.value.toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
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