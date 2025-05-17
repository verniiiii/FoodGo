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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
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

@Composable
fun ProfileScreen() {
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
                    .size(45.dp) // Set the size of the circle
                    .background(GreyLight, shape = CircleShape) // Set the background color and shape
                    ,
                contentAlignment = Alignment.Center // Center the icon within the circle
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = IconGrey3,
                    modifier = Modifier.size(18.dp) // Adjust the size of the icon as needed
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Профиль",
                color = Color.Black,
                fontSize = 17.sp,

            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(45.dp) // Set the size of the circle
                    .background(GreyLight, shape = CircleShape) // Set the background color and shape
                ,
                contentAlignment = Alignment.Center // Center the icon within the circle
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = "Back",
                    tint = IconGrey3,
                    modifier = Modifier.size(18.dp) // Adjust the size of the icon as needed
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your profile image
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(32.dp))
            Column {
                Text(
                    text = "Иван Иванов",
                    color = ProfGrey,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Я люблю фаст фуд",
                    color = PlaceholderGrey,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Scrollable content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp) // Optional: Add padding at the bottom
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Adjust the size as needed
                        .clip(RoundedCornerShape(16.dp)) // Clip the background to rounded corners
                        .background(GreyLight, shape = RoundedCornerShape(16.dp)) // Set the background color and shape
                        .padding(20.dp), // Add padding inside the box
                    contentAlignment = Alignment.Center // Center the content within the box
                ) {
                    Column {
                        ProfileMenuItem(icon = R.drawable.profile, title = "Личная информация", backgroundColor = Profile)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(icon = R.drawable.map, title = "Адреса", backgroundColor = MapColor)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Adjust the size as needed
                        .clip(RoundedCornerShape(16.dp)) // Clip the background to rounded corners
                        .background(GreyLight, shape = RoundedCornerShape(16.dp)) // Set the background color and shape
                        .padding(20.dp), // Add padding inside the box
                    contentAlignment = Alignment.Center // Center the content within the box
                ) {
                    Column {
                        ProfileMenuItem(icon = R.drawable.cart, title = "Корзина", backgroundColor = Cart)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(icon = R.drawable.fav_profile, title = "Избранное", backgroundColor = FavProfile)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(icon = R.drawable.notifications, title = "Уведомления", backgroundColor = Notifications)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(icon = R.drawable.payment, title = "Способ оплаты", backgroundColor = Payment)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Adjust the size as needed
                        .clip(RoundedCornerShape(16.dp)) // Clip the background to rounded corners
                        .background(GreyLight, shape = RoundedCornerShape(16.dp)) // Set the background color and shape
                        .padding(20.dp), // Add padding inside the box
                    contentAlignment = Alignment.Center // Center the content within the box
                ) {
                    Column {
                        ProfileMenuItem(icon = R.drawable.faqs, title = "FAQs", backgroundColor = Faqs)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(icon = R.drawable.user_reviews, title = "Отзывы пользователей", backgroundColor = UserReviews)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(icon = R.drawable.settings, title = "Настройки", backgroundColor = Settings)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Adjust the size as needed
                        .clip(RoundedCornerShape(16.dp)) // Clip the background to rounded corners
                        .background(GreyLight, shape = RoundedCornerShape(16.dp)) // Set the background color and shape
                        .padding(20.dp), // Add padding inside the box
                    contentAlignment = Alignment.Center // Center the content within the box
                ) {
                    Column {
                        ProfileMenuItem(icon = R.drawable.log_out, title = "Выйти", backgroundColor = LogOut)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: Int, title: String, backgroundColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { /* Handle click */ }

    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Set the size of the circle
                .background(White, shape = CircleShape) // Set the background color and shape
            ,
            contentAlignment = Alignment.Center // Center the icon within the circle
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Back",
                tint = backgroundColor,
                modifier = Modifier.size(18.dp) // Adjust the size of the icon as needed
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            color =  ProfGrey,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.right),
            contentDescription = "Back",
            tint = PlaceholderGrey,
            modifier = Modifier.size(10.dp) // Adjust the size of the icon as needed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
