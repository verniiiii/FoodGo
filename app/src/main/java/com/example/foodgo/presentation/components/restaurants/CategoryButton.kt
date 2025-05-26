package com.example.foodgo.presentation.components.restaurants

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.White

@Composable
fun CategoryButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = {onClick()},
        shape = RoundedCornerShape(33.dp),
        modifier = Modifier
            .height(46.dp)
            .then(
                // Добавляем обводку для неактивных элементов
                if (!isSelected) {
                    Modifier.border(2.dp, GreyLight, RoundedCornerShape(33.dp))
                } else {
                    Modifier
                }
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LiteOrange else White,
            contentColor = if (isSelected) White else IconGrey3 // Меняем цвет текста на черный, если неактивная кнопка
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal // Убираем жирное начертание
        )
    }
}
