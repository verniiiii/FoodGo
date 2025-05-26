package com.example.foodgo.presentation.components.filter

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey6
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.White

@Composable
fun FilterOptionButton(text: String, isSelected: Boolean, onClick: () -> Unit, isCircular: Boolean = false) {
    Button(
        onClick = onClick,
        shape = if (isCircular) CircleShape else RoundedCornerShape(33.dp),
        modifier = Modifier
            .height(46.dp)
            .defaultMinSize(minWidth = if (isCircular) 46.dp else 90.dp)
            .border(
                width = if (!isSelected) 2.dp else 0.dp,
                color = if (!isSelected) GreyLight else Color.Transparent,
                shape = if (isCircular) CircleShape else RoundedCornerShape(33.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LiteOrange else White,
            contentColor = if (isSelected) GreyLight else IconGrey6
        ),
        contentPadding = PaddingValues(horizontal = if (isCircular) 0.dp else 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

