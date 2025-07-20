package ru.netology.tco2rus.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("api_token", token).apply()
    }

    fun getToken(): String {
        return prefs.getString("api_token", "") ?: ""
    }

    fun clearToken() {
        prefs.edit().remove("api_token").apply()
    }
}
