package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.user.UserAddressDTO
import com.example.foodgo.presentation.components.ScreenHeader
import com.example.foodgo.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddressesScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddAddressClick: () -> Unit
) {
    val addressesState = viewModel.userAddresses.collectAsState()

    ScreenHeader(stringResource(R.string.addresses), onBackClick = onBackClick) {
        if (addressesState.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.adresses_no))
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

        Button(


            modifier = Modifier.fillMaxWidth(),
            onClick = onAddAddressClick,
        ) {
            Text(text = stringResource(R.string.add_address))
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
            Text(text = address.addressLine, fontSize = 16.sp, color = Color.Gray)
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
                painter = painterResource(id = R.drawable.krest),
                contentDescription = stringResource(R.string.delete_address),
                tint = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
