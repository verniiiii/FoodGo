package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.foodgo.ui.theme.Black

@Composable
fun GreetingSection(userName: String) {
    Row {
        Text(
            text = "$userName, ",
            color = Black,
            fontSize = 16.sp
        )
        Text(
            text = "Добрый День!",
            color = Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}