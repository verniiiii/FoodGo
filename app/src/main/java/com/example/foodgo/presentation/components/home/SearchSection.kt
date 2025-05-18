package com.example.foodgo.presentation.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    searchQuery: MutableState<String>,
    isSearchActive: MutableState<Boolean>,
    searchHistory: SnapshotStateList<String>,
    interactionSource: MutableInteractionSource,
    focusManager: FocusManager,
    keyboardActionHandler: () -> Unit,
    isDialogOpen: MutableState<Boolean>
) {
    var textFieldWidth by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldWidth = coordinates.size.width
            }
    ) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { query ->
                if (!query.contains('\n')) {
                    searchQuery.value = query
                    isSearchActive.value = true
                }
            },
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            placeholder = {
                Text("Поиск ресторанов",
                    color = PlaceholderGrey,
                    fontSize = 14.sp)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = GreyLight
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardActionHandler() }),
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isDialogOpen.value = true },
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (searchQuery.value.isNotBlank()) {
                    IconButton(
                        onClick = {
                            searchQuery.value = ""
                            isSearchActive.value = false
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.krest),
                            contentDescription = "Clear",
                            tint = Color.Gray,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(10.dp)
        )

        if (isSearchActive.value && searchQuery.value.isEmpty()) {
            DropdownMenu(
                expanded = isSearchActive.value,
                onDismissRequest = { isSearchActive.value = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldWidth.toDp() })
                    .background(White)
            ) {
                if (searchHistory.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("История поиска пуста") },
                        onClick = {}
                    )
                } else {
                    Text(
                        text = "История поиска",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    searchHistory.forEach { historyItem ->
                        DropdownMenuItem(
                            text = { Text(historyItem) },
                            onClick = {
                                searchQuery.value = historyItem
                                isSearchActive.value = false
                                keyboardActionHandler()
                            },
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.rating),
                                    contentDescription = "Search history"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
