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
            remove(USER_TOKEN)
            remove(IS_USER_LOGGED_IN) // Явно удаляем флаг входа
            remove(REMEMBER_ME)
        }
    }
}
