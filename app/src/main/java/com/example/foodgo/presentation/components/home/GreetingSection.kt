package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.foodgo.R

@Composable
fun GreetingSection(userName: String) {
    Row {
        Text(
            text = "$userName, ",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(R.string.greeting),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}