package com.example.foodgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.presentation.navigation.AppNavigation
import com.example.foodgo.presentation.viewmodel.ThemeViewModel
import com.example.foodgo.ui.theme.FoodGoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme = themeViewModel.isDarkTheme.collectAsState()

            FoodGoTheme(darkTheme = isDarkTheme.value) {
                AppNavigation(preferencesManager)
            }
        }
    }
}