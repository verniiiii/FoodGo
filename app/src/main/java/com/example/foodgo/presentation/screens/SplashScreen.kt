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
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import com.example.foodgo.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var logoVisible by remember { mutableStateOf(false) }
    var bgVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        logoVisible = true
        delay(1500)
        bgVisible = true
        delay(2000)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = logoVisible, enter = fadeIn(tween(1500))) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.food_logo),
                modifier = Modifier.size(120.dp)
            )
        }

        AnimatedVisibility(visible = bgVisible, enter = fadeIn(tween(1500))) {
            Image(
                painter = painterResource(id = R.drawable.bg_asset),
                contentDescription = stringResource(R.string.splash_back),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
