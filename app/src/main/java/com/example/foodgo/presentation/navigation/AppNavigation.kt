package com.example.foodgo.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO
import com.example.foodgo.presentation.screens.CartScreen
import com.example.foodgo.presentation.screens.OnboardingScreen
import com.example.foodgo.presentation.screens.SplashScreen
import com.example.foodgo.presentation.screens.auth.LoginScreen
import com.example.foodgo.presentation.screens.auth.SignUpScreen
import com.example.foodgo.presentation.screens.home.DishDetailsScreen
import com.example.foodgo.presentation.screens.home.HomeDeliveryScreen
import com.example.foodgo.presentation.screens.home.RestaurantDetailsScreen
import com.example.foodgo.presentation.screens.profile.AddAddressScreen
import com.example.foodgo.presentation.screens.profile.FAQScreen
import com.example.foodgo.presentation.screens.profile.FavoritesScreen
import com.example.foodgo.presentation.screens.profile.OrderDetailsScreen
import com.example.foodgo.presentation.screens.profile.OrdersScreen
import com.example.foodgo.presentation.screens.profile.PersonalInfoScreen
import com.example.foodgo.presentation.screens.profile.ProfileScreen
import com.example.foodgo.presentation.screens.profile.UserAddressesScreen
import com.google.gson.Gson
import java.net.URLDecoder

@Composable
fun AppNavigation(preferencesManager: PreferencesManager) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.SPLASH
    ) {

        // ----------------------
        // Splash & Onboarding
        // ----------------------
        composable(Destination.SPLASH) {
            SplashScreen(
                onSplashComplete = {
                    val route = when {
                        preferencesManager.isRememberMeEnabled() &&
                                preferencesManager.getUserToken() != null -> Destination.HOME_DELIVERY
                        !preferencesManager.isOnboardingCompleted() -> Destination.ONBOARDING
                        else -> Destination.LOGIN
                    }
                    navController.navigate(route) {
                        popUpTo(Destination.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Destination.ONBOARDING) {
            OnboardingScreen(
                onGetStartedClick = {
                    preferencesManager.setOnboardingCompleted(true)
                    navController.navigate(Destination.LOGIN) {
                        popUpTo(Destination.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // ----------------------
        // Auth Screens
        // ----------------------
        composable(Destination.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    preferencesManager.setUserLoggedIn(true)
                    navController.navigate(Destination.HOME_DELIVERY) {
                        popUpTo(0)
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Destination.SIGN_UP)
                }
            )
        }

        composable(Destination.SIGN_UP) {
            SignUpScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // ----------------------
        // Home + Restaurant
        // ----------------------
        composable(Destination.HOME_DELIVERY) {
            HomeDeliveryScreen(
                onProfile = {navController.navigate(Destination.PROFILE)},
                onCart = {navController.navigate(Destination.CART)},
                onRestaurant = {restaurant ->
                    val restaurantJson = Uri.encode(Gson().toJson(restaurant))
                    navController.navigate("${Destination.RESTAURANT_DETAILS}/$restaurantJson")
                }
            )
        }

        composable(
            route = "${Destination.RESTAURANT_DETAILS}/{restaurantJson}",
            arguments = listOf(navArgument("restaurantJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("restaurantJson") ?: ""
            val decodedJson = URLDecoder.decode(encodedJson, "UTF-8")
            val restaurant = Gson().fromJson(decodedJson, RestaurantWithPhotosDTO::class.java)
            RestaurantDetailsScreen(
                restaurant,
                onDishDetail = {dishId ->
                    navController.navigate("dishDetails/${dishId}")
                },
                onBack = {
                    navController.navigate(Destination.HOME_DELIVERY) {
                        popUpTo(Destination.HOME_DELIVERY) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "${Destination.DISH_DETAILS}/{dishId}",
            arguments = listOf(navArgument("dishId") { type = NavType.IntType })
        ) { backStackEntry ->
            val dishId = backStackEntry.arguments?.getInt("dishId") ?: 1
            DishDetailsScreen(dishId, onBack = { navController.popBackStack() })
        }

        composable(Destination.CART) {
            CartScreen(onBack = { navController.popBackStack() })
        }

        // ----------------------
        // Profile
        // ----------------------
        composable(Destination.PROFILE) {
            ProfileScreen(
                onNavigateToPersonalInfo = { navController.navigate(Destination.PERSONAL_INFO) },
                onNavigateToAddresses = { navController.navigate(Destination.ADDRESSES) },
                onNavigateToCart = { navController.navigate(Destination.CART) },
                onNavigateToFavorites = { navController.navigate(Destination.FAVORITES) },
                onNavigateToFaqs = { navController.navigate(Destination.FAQS) },
                onLogout = {
                    preferencesManager.clearUserSession()
                    navController.navigate(Destination.LOGIN) {
                        popUpTo(0)
                    }
                },
                onNavigateToOrders = {
                    navController.navigate(Destination.ORDERS)
                },
                onBack = {
                    navController.navigate(Destination.HOME_DELIVERY) {
                        popUpTo(Destination.HOME_DELIVERY) { inclusive = true }
                    }
                }
            )
        }

        composable(Destination.PERSONAL_INFO) {
            PersonalInfoScreen(
                onBack = {
                    navController.navigate(Destination.PROFILE) {
                        popUpTo(Destination.PROFILE) { inclusive = true }
                    }
                }
            )
        }

        composable(Destination.ADDRESSES) {
            UserAddressesScreen(
                onAddAddressClick = { navController.navigate(Destination.ADD_ADDRESS) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Destination.ADD_ADDRESS) {
            AddAddressScreen(
                onBack = { navController.navigate(Destination.ADDRESSES) {
                    popUpTo(Destination.ADDRESSES) { inclusive = true }
                } }
            )
        }

        composable(Destination.FAVORITES) {
            FavoritesScreen(
                onDish = {dishId ->
                    navController.navigate("dishDetails/$dishId")
                },
                    onBack = { navController.popBackStack() }
            )
        }

        composable(Destination.FAQS) {
            FAQScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destination.ORDERS) {
            OrdersScreen(
                preferencesManager = preferencesManager,
                onOrderClick = { order ->
                    navController.navigate("orderDetails/${order.order.orderId}")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Destination.ORDER_DETAILS}/{orderId}",
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getLong("orderId") ?: -1L
            OrderDetailsScreen(orderId, onBack = { navController.popBackStack() })
        }
    }
}


