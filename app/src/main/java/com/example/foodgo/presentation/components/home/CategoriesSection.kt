package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.CategoryDTO

@Composable
fun CategoriesSection(
    categories: List<CategoryDTO>,
    selectedCategory: MutableState<String>
) {
    Text(
        text = stringResource(R.string.all_categories),
        color = MaterialTheme.colorScheme.onBackground,
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