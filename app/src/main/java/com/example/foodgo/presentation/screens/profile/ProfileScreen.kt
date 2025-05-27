package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.R
import com.example.foodgo.presentation.components.ScreenHeader
import com.example.foodgo.presentation.components.profile.ProfileMenuItem
import com.example.foodgo.presentation.viewmodel.ProfileViewModel
import com.example.foodgo.presentation.viewmodel.ThemeViewModel
import com.example.foodgo.ui.theme.Cart
import com.example.foodgo.ui.theme.Faqs
import com.example.foodgo.ui.theme.FavProfile
import com.example.foodgo.ui.theme.LogOut
import com.example.foodgo.ui.theme.MapColor
import com.example.foodgo.ui.theme.Notifications
import com.example.foodgo.ui.theme.Profile

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    onNavigateToPersonalInfo: () -> Unit = {},
    onNavigateToAddresses: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToFavorites: () -> Unit = {},
    onNavigateToFaqs: () -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToOrders: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val profileState = viewModel.profileState.collectAsState()

    ScreenHeader("Профиль", onBackClick = onBack) { // Profile Info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Добавляем AsyncImage для загрузки аватарки по URL
            AsyncImage(
                model = "https://yandex-images.clstorage.net/9PgjM5449/a1039b9is/0f8sdfHQrKf_Z8Mx4tN5fy3LY3pcpdsQVHk07gU_mRAXK7Y0AxjjpKzV8Y_8U4xHSvESKX9kk-xRXga23k-o8MTieDDA_bj6rhLDRLLzdWslvhZ2GNSzASAQPZ45cv04My-i3O-VOkkJCTRmIaJUO17RE3EOVYbsoMJD20-2-YiJw7D1TTBtR9s7ljVmM9nbgeJzcmCshyEI8WlKtXPNXYZ62uCXRR7ZsQWgUpG2YAdWQSPRw3UabFgfHxOznq1AlTOACTXQzb-v58qBsuv5pxG6y3potKOhCGGZqil3VckSOkbAiy0yBVl9bPZx7nhn4iEvATd9ZpToo3dTUyI9ABXvIAFl1DkHz-uKRe4ziVcpks9OcDhL4WjhcbKZi7npCnoOyF_JkmFVleDekcZoO979w4EH1Z7owNtH38PejRS1IxyJuaSFK_uXah1mm8l_DTKTAkBc1z0QWUE2ZW_1VWomdmSb-QIdwXksVunu4Bf2pSN5wylybJyzj-tfyrF0RXustS1IIXuXf6Jhpm9RhwGuF1ZQWCd9PI11BsHjRRlWvjLsB4026XmV6FIBWigTCkHT4dMJHuTAWycvy16hlL0rJLmp0DmvG0dWFT4TfaNdZgeiAMBT0Xi9QUpRP6ml8s6GjB-hJnExbaTyFRYsD2b9Gzm3RW4YxB8Pk09WMYSBmxRpXdjtd6NTFklCB9GzYTofatw40-k0mQUWzb9FBQbGogwHBSYVnbE87okqQNdmVbfhQ_1iBJAna5fDUrk8tYeUTWWkid_HLyIlDh-VDzFmS374zE_9hJUBWuWDQZ3mIiqsTz16CXGZGEa10rzPQrGX6S9JAtzEl887X959AO2DFDVxJMX3Y-O-NWaLjespPpsm8FhPjaT1fcJVcwWJrgLG6MPBio1F0RhqiVaMHwqBY3W7fcKgSOdjbyPOfYhJx-RFcURt_4e3Qp3Ow8X_qbYbBuzwYw0YwfVKmXtM", // URL или дефолтная картинка
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background), // Запасной вариант при ошибке загрузки
                placeholder = painterResource(id = R.drawable.ic_launcher_background) // Плейсхолдер во время загрузки
            )

            Spacer(modifier = Modifier.width(32.dp))
            Column {
                Text(
                    text = profileState.value.username,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = profileState.value.description,
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Scrollable content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.profile,
                            title = "Личная информация",
                            backgroundColor = Profile,
                            onClick = onNavigateToPersonalInfo
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.map,
                            title = "Адреса",
                            backgroundColor = MapColor,
                            onClick = onNavigateToAddresses
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.cart,
                            title = "Корзина",
                            backgroundColor = Cart,
                            onClick = onNavigateToCart
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.fav_profile,
                            title = "Избранное",
                            backgroundColor = FavProfile,
                            onClick = onNavigateToFavorites
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.delivery,
                            title = "Мои заказы",
                            backgroundColor = Notifications,
                            onClick = onNavigateToOrders
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ProfileMenuItem(
                        icon = R.drawable.faqs,
                        title = "FAQs",
                        backgroundColor = Faqs,
                        onClick = onNavigateToFaqs
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.log_out,
                            title = "Выйти",
                            backgroundColor = LogOut,
                            onClick = {
                                viewModel.logout()
                                onLogout()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { themeViewModel.toggleTheme() },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Сменить тему")
                    }
                }

            }
        }
    }
}

