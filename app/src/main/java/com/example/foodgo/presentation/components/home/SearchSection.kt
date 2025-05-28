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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.presentation.viewmodel.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    searchQuery: MutableState<String>,
    isSearchActive: MutableState<Boolean>,
    searchHistory: List<String>,
    interactionSource: MutableInteractionSource,
    keyboardActionHandler: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    isDialogOpen: MutableState<Boolean>
) {
    var textFieldWidth by remember { mutableIntStateOf(0) }

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
                Text(
                    stringResource(R.string.search_rest),
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 14.sp)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background
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
                    contentDescription = stringResource(R.string.search_rest),
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
                            contentDescription = stringResource(R.string.clear),
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
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {
                if (searchHistory.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.searchHistory_empty)) },
                        onClick = {}
                    )
                } else {
                    Text(
                        text = stringResource(R.string.searchHistory),
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
                                    contentDescription = stringResource(R.string.searchHistory)
                                )
                            }
                        )
                    }

                    DropdownMenuItem(
                        text = {
                            Text(
                                stringResource(R.string.clearSearchHistory),
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            homeViewModel.clearSearchHistory()
                            isSearchActive.value = false
                        }
                    )
                }
            }
        }
    }
}
