package com.example.foodgo.presentation.components

import androidx.compose.foundation.border
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.foodgo.R



import androidx.compose.material3.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey6
import com.example.foodgo.ui.theme.LiteOrange
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.White

@Composable
fun FilterDialog(
    isDialogOpen: MutableState<Boolean>,
    onApplyFilters: (String, String, String, Int) -> Unit
) {
    var selectedOffer by remember { mutableStateOf("") }
    var selectedDeliveryTime by remember { mutableStateOf("10-15 min") }
    var selectedPricing by remember { mutableStateOf("$$") }
    var selectedRating by remember { mutableStateOf(4) }

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Заголовок
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

                    // Offers Section
                    Text(text = "ТЕГИ", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(13.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilterOptionButton(
                            text = "Доставка",
                            isSelected = selectedOffer == "Доставка",
                            onClick = { selectedOffer = "Доставка" }
                        )
                        FilterOptionButton(
                            text = "Pick Up",
                            isSelected = selectedOffer == "Pick Up",
                            onClick = { selectedOffer = "Pick Up" }
                        )
                        FilterOptionButton(
                            text = "Скидка",
                            isSelected = selectedOffer == "Скидка",
                            onClick = { selectedOffer = "Скидка" }
                        )
                    }
                    Spacer(modifier = Modifier.height(9.dp))

                    FilterOptionButton(
                        text = "Доступна онлайн-оплата",
                        isSelected = selectedOffer == "Доступна онлайн-оплата",
                        onClick = { selectedOffer = "Доступна онлайн-оплата" }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Delivery Time Section
                    Text(text = "ВРЕМЯ ДОСТАВКИ", fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilterOptionButton(
                            text = "10-15 мин",
                            isSelected = selectedDeliveryTime == "10-15 мин",
                            onClick = { selectedDeliveryTime = "10-15 мин" }
                        )
                        FilterOptionButton(
                            text = "20 мин",
                            isSelected = selectedDeliveryTime == "20 мин",
                            onClick = { selectedDeliveryTime = "20 мин" }
                        )
                        FilterOptionButton(
                            text = "30 мин",
                            isSelected = selectedDeliveryTime == "30 мин",
                            onClick = { selectedDeliveryTime = "30 мин" }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Pricing Section
                    Text(text = "PRICING", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(13.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilterOptionButton(
                            text = "$",
                            isSelected = selectedPricing == "$",
                            onClick = { selectedPricing = "$" },
                            isCircular = true
                        )
                        FilterOptionButton(
                            text = "$$",
                            isSelected = selectedPricing == "$$",
                            onClick = { selectedPricing = "$$" },
                            isCircular = true
                        )
                        FilterOptionButton(
                            text = "$$$",
                            isSelected = selectedPricing == "$$$",
                            onClick = { selectedPricing = "$$$" },
                            isCircular = true
                        )
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
                                onClick = { selectedRating = starIndex },
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
                                    contentColor = if (starIndex <= selectedRating) GreyLight else IconGrey6
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.rating),
                                    contentDescription = "$starIndex star",
                                    tint = if (starIndex <= selectedRating) Orange else GreyLight,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(31.dp))

                    Button(
                        onClick = {
                            onApplyFilters(selectedOffer, selectedDeliveryTime, selectedPricing, selectedRating)
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
                }
            }
        }
    }
}

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

