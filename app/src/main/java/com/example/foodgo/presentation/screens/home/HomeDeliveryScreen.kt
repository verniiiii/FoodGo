package com.example.foodgo.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.CategoryDTO
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO
import com.example.foodgo.presentation.components.filter.FilterDialog
import com.example.foodgo.presentation.components.home.CategoriesSection
import com.example.foodgo.presentation.components.home.ErrorMessage
import com.example.foodgo.presentation.components.home.GreetingSection
import com.example.foodgo.presentation.components.home.HeaderSection
import com.example.foodgo.presentation.components.home.LoadingIndicator
import com.example.foodgo.presentation.components.home.RestaurantsSection
import com.example.foodgo.presentation.components.home.SearchSection
import com.example.foodgo.presentation.viewmodel.BasketViewModel
import com.example.foodgo.presentation.viewmodel.UserViewModel
import com.example.foodgo.presentation.viewmodel.home.HomeViewModel

@Composable
fun HomeDeliveryScreen(
    onProfile: () -> Unit,
    onCart: () -> Unit,
    onRestaurant: (rest: RestaurantWithPhotosDTO) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    basketViewModel: BasketViewModel = hiltViewModel()
) {

    val selectedCategory = remember { mutableStateOf("Всё") }
    val restaurantList = homeViewModel.restaurants.collectAsState()
    val isLoading = homeViewModel.isLoading.collectAsState()
    val searchQuery = remember { mutableStateOf("") }
    val isSearchActive = remember { mutableStateOf(false) }
    val searchHistory by homeViewModel.searchHistory.collectAsState()



    val cartItemCount = homeViewModel.cartItemCount.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadSearchHistory()
        homeViewModel.loadCartItemCount()
        basketViewModel.updateCartItemCount(cartItemCount.value)
        homeViewModel.cartItemCount.collect { count ->
            basketViewModel.updateCartItemCount(count)
        }
    }

    println(cartItemCount.value)



    val categoryState = homeViewModel.categories.collectAsState()
    val categories = listOf(
        CategoryDTO(
            name = stringResource(R.string.all),
            photoUrl = stringResource(R.string.all_url),
            isSelected = selectedCategory.value == stringResource(R.string.all)
        )
    ) + categoryState.value.map {
        it.copy(isSelected = it.name == selectedCategory.value)
    }

    val addresses = userViewModel.userAddresses.collectAsState()
    val selectedAddress = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val isDialogOpen = remember { mutableStateOf(false) }
    val userName = userViewModel.userName.collectAsState()

    LaunchedEffect(addresses.value) {
        if (selectedAddress.value.isEmpty() && addresses.value.isNotEmpty()) {
            selectedAddress.value = addresses.value[0].addressLine
        }
    }

    val focusManager = LocalFocusManager.current

    val keyboardActionHandler = {
        val query = searchQuery.value.trim()
        if (query.isNotEmpty()) {
            homeViewModel.saveSearchQuery(query)
        }
        focusManager.clearFocus()
    }


    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        isSearchActive.value = isFocused || searchQuery.value.isNotEmpty()
    }

    val filterCriteria by homeViewModel.filterCriteria.collectAsState()

    val filteredRestaurants = remember(
        restaurantList.value,
        selectedCategory.value,
        searchQuery.value,
        filterCriteria
    ) {
        homeViewModel.filterRestaurants(restaurantList.value, selectedCategory.value, searchQuery.value, filterCriteria)
    }

    var selectedDeliveryTime by remember { mutableStateOf("10 мин") }
    var selectedRating by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(top = 50.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        HeaderSection(
            onProfile = onProfile,
            onCart = onCart,
            selectedAddress = selectedAddress,
            expanded = expanded,
            addresses = addresses
        )

        Spacer(modifier = Modifier.height(24.dp))

        GreetingSection(userName = userName.value.toString())

        Spacer(modifier = Modifier.height(16.dp))

        SearchSection(
            searchQuery = searchQuery,
            isSearchActive = isSearchActive,
            searchHistory = searchHistory,
            interactionSource = interactionSource,
            keyboardActionHandler = keyboardActionHandler,
            isDialogOpen = isDialogOpen
        )

        if (isLoading.value) {
            LoadingIndicator()
            return@Column
        }

        val errorMessage by homeViewModel.error.collectAsState()

        if (!errorMessage.isNullOrEmpty()) {
            ErrorMessage() {
                homeViewModel.refreshData()
                userViewModel.loadUserData()
            }
            return@Column
        }



        if (isDialogOpen.value) {
            FilterDialog(
                isDialogOpen = isDialogOpen,
                deliveryTime = selectedDeliveryTime,
                rating = selectedRating,
                onDeliveryTimeChange = { selectedDeliveryTime = it },
                onRatingChange = { selectedRating = it },
                onApplyFilters = { deliveryTime, rating ->
                    val maxDeliveryTime = when (deliveryTime) {
                        "10 мин" -> 10
                        "20 мин" -> 20
                        "30 мин" -> 30
                        else -> Int.MAX_VALUE
                    }
                    homeViewModel.applyFilters(
                        HomeViewModel.FilterCriteria(
                            minRating = rating,
                            maxDeliveryTime = maxDeliveryTime
                        )
                    )
                    isDialogOpen.value = false
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CategoriesSection(categories = categories, selectedCategory = selectedCategory)

        Spacer(modifier = Modifier.height(32.dp))

        RestaurantsSection(
            filteredRestaurants = filteredRestaurants,
            onRestaurant = onRestaurant
        )
    }
}



















