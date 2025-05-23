package com.example.foodgo

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FoodGoPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val ONBOARDING_COMPLETED = "onboarding_completed"
        private const val IS_USER_LOGGED_IN = "is_user_logged_in"
        private const val REMEMBER_ME = "remember_me"
        private const val USER_TOKEN = "user_token"
        private const val USER_ID = "user_id"
        private const val USERNAME = "username"
        private const val LOGIN = "login"
        private const val DESCRIPTION = "description"
        private const val PHONE = "phone"
        private const val NOTIFICATIONS = "notifications"
        private const val SEARCH_HISTORY = "search_history"
        private const val SEARCH_HISTORY_MAX_SIZE = 10
    }

    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(isCompleted: Boolean) {
        sharedPreferences.edit { putBoolean(ONBOARDING_COMPLETED, isCompleted) }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN, false)
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit { putBoolean(IS_USER_LOGGED_IN, isLoggedIn) }
    }

    fun isRememberMeEnabled(): Boolean {
        return sharedPreferences.getBoolean(REMEMBER_ME, false)
    }

    fun setRememberMeEnabled(isEnabled: Boolean) {
        sharedPreferences.edit { putBoolean(REMEMBER_ME, isEnabled) }
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }

    fun setUserToken(token: String) {
        sharedPreferences.edit { putString(USER_TOKEN, token) }
    }

    fun clearUserSession() {
        sharedPreferences.edit {
            remove(USERNAME)
            remove(LOGIN)
            remove(DESCRIPTION)
            remove(PHONE)
            remove(NOTIFICATIONS)
        }
    }


    fun saveUserData(
        id: Int,
        username: String?,
        login: String?,
        description: String?,
        phone: String?,
        notificationsEnabled: Boolean
    ) {
        sharedPreferences.edit {
            putInt(USER_ID, id)
            putString(USERNAME, username)
            putString(LOGIN, login)
            putString(DESCRIPTION, description)
            putString(PHONE, phone)
            putBoolean(NOTIFICATIONS, notificationsEnabled)
        }
    }

    fun getUsername(): String? = sharedPreferences.getString(USERNAME, null)
    fun getLogin(): String? = sharedPreferences.getString(LOGIN, null)
    fun getDescription(): String? = sharedPreferences.getString(DESCRIPTION, null)
    fun getPhone(): String? = sharedPreferences.getString(PHONE, null)
    fun isNotificationsEnabled(): Boolean = sharedPreferences.getBoolean(NOTIFICATIONS, false)


    // Сохраняет историю поиска (максимум SEARCH_HISTORY_MAX_SIZE записей)
    fun saveSearchHistory(history: List<String>) {
        val limitedHistory = history.take(SEARCH_HISTORY_MAX_SIZE)
        sharedPreferences.edit {
            putStringSet(SEARCH_HISTORY, limitedHistory.toSet())
        }
    }

    // Получает историю поиска
    fun getSearchHistory(): List<String> {
        return sharedPreferences.getStringSet(SEARCH_HISTORY, emptySet())?.toList() ?: emptyList()
    }

    // Добавляет новый запрос в историю поиска
    fun addToSearchHistory(query: String) {
        if (query.isBlank()) return

        val currentHistory = getSearchHistory().toMutableList()

        // Удаляем дубликаты
        currentHistory.removeAll { it.equals(query, ignoreCase = true) }

        // Добавляем в начало
        currentHistory.add(0, query)

        // Сохраняем только последние SEARCH_HISTORY_MAX_SIZE запросов
        saveSearchHistory(currentHistory.take(SEARCH_HISTORY_MAX_SIZE))
    }

    // Очищает историю поиска
    fun clearSearchHistory() {
        sharedPreferences.edit {
            remove(SEARCH_HISTORY)
        }
    }
}
