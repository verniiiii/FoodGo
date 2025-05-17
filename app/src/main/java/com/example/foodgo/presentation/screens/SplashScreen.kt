package com.example.foodgo.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.animation.fadeIn
import com.example.foodgo.R
import com.example.foodgo.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var logoVisible by remember { mutableStateOf(false) }
    var bgVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)  // Логотип появляется через 0.5 сек
        logoVisible = true
        delay(1500) // Фон появляется через 1.5 сек после логотипа
        bgVisible = true
        delay(2000) // Ещё 2 сек и переход дальше
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White), // Используем белый цвет из темы
        contentAlignment = Alignment.Center
    ) {
        // Анимированное появление логотипа (сначала)
        AnimatedVisibility(visible = logoVisible, enter = fadeIn(tween(1500))) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Food Logo",
                modifier = Modifier.size(120.dp)
            )
        }

        // Анимированное появление фона (позже)
        AnimatedVisibility(visible = bgVisible, enter = fadeIn(tween(1500))) {
            Image(
                painter = painterResource(id = R.drawable.bg_asset),
                contentDescription = "Background Design",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
