package com.example.foodgo

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManager(@ApplicationContext private val appContext: Context) {

    private val sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences("FoodGoPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val ONBOARDING_COMPLETED = "onboarding_completed"
        private const val IS_USER_LOGGED_IN = "is_user_logged_in"
        private const val REMEMBER_ME = "remember_me"
        private const val USER_TOKEN = "user_token"
        private const val USER_ID = "user_id"
        private const val USERNAME = "username"
        private const val LOGIN = "login"
        private const val DESCRIPTION = "description"
        private const val SEARCH_HISTORY = "search_history"
        private const val SEARCH_HISTORY_MAX_SIZE = 10
        private const val CART_ITEMS = "cart_items"
        private  val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    // --- Onboarding ---
    fun isOnboardingCompleted(): Boolean =
        sharedPreferences.getBoolean(ONBOARDING_COMPLETED, false)

    fun setOnboardingCompleted(isCompleted: Boolean) {
        sharedPreferences.edit { putBoolean(ONBOARDING_COMPLETED, isCompleted) }
    }

    // --- Authentication ---
    fun setUserLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit { putBoolean(IS_USER_LOGGED_IN, isLoggedIn) }
    }

    fun isRememberMeEnabled(): Boolean =
        sharedPreferences.getBoolean(REMEMBER_ME, false)

    fun setRememberMeEnabled(isEnabled: Boolean) {
        sharedPreferences.edit { putBoolean(REMEMBER_ME, isEnabled) }
    }

    fun getUserToken(): String? =
        sharedPreferences.getString(USER_TOKEN, null)

    fun setUserToken(token: String) {
        sharedPreferences.edit { putString(USER_TOKEN, token) }
    }

    fun clearUserSession() {
        sharedPreferences.edit {
            remove(USERNAME)
            remove(LOGIN)
            remove(DESCRIPTION)
            remove(USER_TOKEN)
            setUserLoggedIn(false)
        }
    }

    // --- User Data ---
    fun saveUserData(id: Int, username: String?, login: String?, description: String?) {
        sharedPreferences.edit {
            putInt(USER_ID, id)
            putString(USERNAME, username)
            putString(LOGIN, login)
            putString(DESCRIPTION, description)
        }
    }

    fun getUsername(): String? = sharedPreferences.getString(USERNAME, null)
    fun getUserId(): Int = sharedPreferences.getInt(USER_ID, 1)
    fun getLogin(): String? = sharedPreferences.getString(LOGIN, null)
    fun getDescription(): String? = sharedPreferences.getString(DESCRIPTION, null)

    // --- Search History ---
    fun saveSearchHistory(history: List<String>) {
        val limitedHistory = history.take(SEARCH_HISTORY_MAX_SIZE)
        sharedPreferences.edit {
            putStringSet(SEARCH_HISTORY, limitedHistory.toSet())
        }
    }

    fun getSearchHistory(): List<String> =
        sharedPreferences.getStringSet(SEARCH_HISTORY, emptySet())?.toList() ?: emptyList()

    fun clearSearchHistory() {
        sharedPreferences.edit { remove(SEARCH_HISTORY) }
    }


    // --- Cart ---
    fun saveCartItems(cart: Map<String, Int>) {
        val json = Gson().toJson(cart)
        sharedPreferences.edit { putString(CART_ITEMS, json) }
    }

    fun getCartItems(): Map<String, Int> {
        val json = sharedPreferences.getString(CART_ITEMS, null)
        return if (json != null) {
            val type = object : TypeToken<Map<String, Int>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyMap()
        }
    }

    fun addToCart(dishId: Int, size: String?, quantity: Int) {
        val currentCart = getCartItems().toMutableMap()
        val key = if (size != null) "$dishId|$size" else "$dishId"
        currentCart[key] = (currentCart[key] ?: 0) + quantity
        saveCartItems(currentCart)
    }

    fun clearCart() {
        sharedPreferences.edit { remove(CART_ITEMS) }
    }

    private val Context.dataStore by preferencesDataStore(name = "settings")

    val themeFlow: Flow<Boolean> = appContext.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME] ?: false
        }

    suspend fun setTheme(value: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[DARK_THEME] = value
        }
    }
}
