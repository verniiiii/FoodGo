package com.example.foodgo.presentation.components.filter

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.foodgo.R
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey6
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.White

@Composable
fun FilterDialog(
    isDialogOpen: MutableState<Boolean>,
    deliveryTime: String,
    rating: Int,
    onDeliveryTimeChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onApplyFilters: (String, Int) -> Unit
) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Заголовок и кнопка закрытия - без изменений
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Фильтр", fontSize = 17.sp)
                        IconButton(onClick = { isDialogOpen.value = false }) {
                            Icon(
                                painter = painterResource(id = R.drawable.krest),
                                contentDescription = "Close",
                                tint = IconGrey6,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(19.dp))

                    // Delivery Time Section
                    Text(text = "ВРЕМЯ ДОСТАВКИ", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("10 мин", "20 мин", "30 мин").forEach { time ->
                            FilterOptionButton(
                                text = time,
                                isSelected = deliveryTime == time,
                                onClick = { onDeliveryTimeChange(time) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Rating Section
                    Text(text = "RATING", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (1..5).forEach { starIndex ->
                            Button(
                                onClick = { onRatingChange(starIndex) },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(48.dp)
                                    .border(
                                        width = 2.dp,
                                        color = GreyLight,
                                        shape = CircleShape
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White,
                                    contentColor = if (starIndex <= rating) GreyLight else IconGrey6
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.rating),
                                    contentDescription = "$starIndex star",
                                    tint = if (starIndex <= rating) Orange else GreyLight,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(31.dp))

                    Button(
                        onClick = {
                            onApplyFilters(deliveryTime, rating)
                            isDialogOpen.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "ПРИМЕНИТЬ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            onApplyFilters("", 0)
                            isDialogOpen.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "ОТМЕНИТЬ ФИЛЬТРЫ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
            }
        }
    }
}
