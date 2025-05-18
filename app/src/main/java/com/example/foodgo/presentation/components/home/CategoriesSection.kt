package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.data.remote.dto.CategoryDTO
import com.example.foodgo.ui.theme.ProfGrey

@Composable
fun CategoriesSection(
    categories: List<CategoryDTO>,
    selectedCategory: MutableState<String>
) {
    Text(
        text = "Все Категории",
        color = ProfGrey,
        fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(20.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        items(categories) { category ->
            FoodDeliveryCategoryButton(
                text = category.name,
                isSelected = category.isSelected,
                icon = category.photoUrl,
                onClick = {
                    selectedCategory.value = category.name
                }
            )
        }
    }
}