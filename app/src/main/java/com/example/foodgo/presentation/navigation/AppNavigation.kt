package com.example.foodgo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodgo.PreferencesManager
import com.example.foodgo.presentation.screens.CartScreen
import com.example.foodgo.presentation.screens.SplashScreen
import com.example.foodgo.presentation.screens.auth.LoginScreen
import com.example.foodgo.presentation.screens.auth.SignUpScreen
import com.example.foodgo.presentation.screens.home.DishDetailsScreen
import com.example.foodgo.presentation.screens.home.HomeDeliveryScreen
import com.example.foodgo.presentation.screens.home.RestaurantDetailsScreen
import com.example.foodgo.presentation.screens.OnboardingScreen
import com.example.foodgo.presentation.screens.profile.ProfileScreen

@Composable
fun AppNavigation(preferencesManager: PreferencesManager) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.SPLASH
    ) {
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

        composable(Destination.HOME_DELIVERY) {
            HomeDeliveryScreen(navController)
        }

        composable(Destination.RESTAURANT_DETAILS) {
            RestaurantDetailsScreen(navController)
        }

        composable(Destination.DISH_DETAILS) {
            DishDetailsScreen()
        }

        composable(Destination.CART) {
            CartScreen()
        }

        composable(Destination.PROFILE) {
            ProfileScreen()
        }
    }
}