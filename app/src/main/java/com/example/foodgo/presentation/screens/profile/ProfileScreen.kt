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
import androidx.compose.ui.res.stringResource
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

    ScreenHeader(stringResource(R.string.profile), onBackClick = onBack) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = stringResource(R.string.profile_url),
                contentDescription = stringResource(R.string.profile_image),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.profile,
                            title = stringResource(R.string.profile_info),
                            backgroundColor = Profile,
                            onClick = onNavigateToPersonalInfo
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.map,
                            title = stringResource(R.string.addresses),
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
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.cart,
                            title = stringResource(R.string.cart),
                            backgroundColor = Cart,
                            onClick = onNavigateToCart
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.fav_profile,
                            title = stringResource(R.string.favorite),
                            backgroundColor = FavProfile,
                            onClick = onNavigateToFavorites
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileMenuItem(
                            icon = R.drawable.delivery,
                            title = stringResource(R.string.my_orders),
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
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ProfileMenuItem(
                        icon = R.drawable.faqs,
                        title = stringResource(R.string.faqs),
                        backgroundColor = Faqs,
                        onClick = onNavigateToFaqs
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        ProfileMenuItem(
                            icon = R.drawable.log_out,
                            title = stringResource(R.string.logOut),
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
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { themeViewModel.toggleTheme() },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(stringResource(R.string.change_theme))
                    }
                }

            }
        }
    }
}

