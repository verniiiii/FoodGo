package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.user.UserAddressDTO
import com.example.foodgo.presentation.components.ScreenHeader
import com.example.foodgo.presentation.viewmodel.UserViewModel
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddressesScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddAddressClick: () -> Unit
) {
    val addressesState = viewModel.userAddresses.collectAsState()

    ScreenHeader("Адреса", onBackClick = onBackClick) {
        // Список адресов
        if (addressesState.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Адреса отсутствуют")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(addressesState.value) { address ->
                    AddressItem(address = address, onDelete = {
                        viewModel.deleteAddress(it)
                    })
                    Divider()
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка "Добавить адрес"
        Button(
            onClick = onAddAddressClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Добавить адрес")
        }
    }
}

@Composable
fun AddressItem(address: UserAddressDTO, onDelete: (UserAddressDTO) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = address.addressLine, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = address.city, fontSize = 14.sp, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            address.comment?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
        IconButton(onClick = { onDelete(address) }) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.krest), // Добавь иконку удаления в ресурсы
                contentDescription = "Удалить адрес",
                tint = Color.Red
            )
        }
    }
}
