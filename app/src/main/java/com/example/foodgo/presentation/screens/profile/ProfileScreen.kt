package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.R
import com.example.foodgo.presentation.viewmodel.ProfileViewModel
import com.example.foodgo.ui.theme.Cart
import com.example.foodgo.ui.theme.Faqs
import com.example.foodgo.ui.theme.FavProfile
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.LogOut
import com.example.foodgo.ui.theme.MapColor
import com.example.foodgo.ui.theme.Notifications
import com.example.foodgo.ui.theme.Payment
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.ProfGrey
import com.example.foodgo.ui.theme.Profile
import com.example.foodgo.ui.theme.Settings
import com.example.foodgo.ui.theme.UserReviews
import com.example.foodgo.ui.theme.White
import org.jetbrains.annotations.Async

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToPersonalInfo: () -> Unit = {},
    onNavigateToAddresses: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToFavorites: () -> Unit = {},
    onNavigateToFaqs: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val profileState = viewModel.profileState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp, bottom = 29.dp, start = 24.dp, end = 24.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(GreyLight, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = IconGrey3,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Профиль",
                color = Color.Black,
                fontSize = 17.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Добавляем AsyncImage для загрузки аватарки по URL
            AsyncImage(
                model = R.drawable.ic_launcher_background, // URL или дефолтная картинка
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
                    color = ProfGrey,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = profileState.value.description,
                    color = PlaceholderGrey,
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
                        .background(GreyLight, shape = RoundedCornerShape(16.dp))
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
                        .background(GreyLight, shape = RoundedCornerShape(16.dp))
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
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GreyLight, shape = RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.faqs,
                            title = "FAQs",
                            backgroundColor = Faqs,
                            onClick = onNavigateToFaqs
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.settings,
                            title = "Настройки",
                            backgroundColor = Settings,
                            onClick = onNavigateToSettings
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GreyLight, shape = RoundedCornerShape(16.dp))
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
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: Int,
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Back",
                tint = backgroundColor,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            color = ProfGrey,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.right),
            contentDescription = "Back",
            tint = PlaceholderGrey,
            modifier = Modifier.size(10.dp)
        )
    }
}
