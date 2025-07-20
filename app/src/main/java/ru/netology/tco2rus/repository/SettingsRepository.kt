package ru.netology.tco2rus.repository

import android.content.SharedPreferences
import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.dto.ChangePasswordRequest
import ru.netology.tco2rus.api.dto.HistoryOrderDto
import ru.netology.tco2rus.data.HistoryOrder
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager,
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val PREFS_NOTIFICATIONS = "notifications_enabled"
        private const val PREFS_SOUND = "sound_enabled"
    }

    suspend fun changePassword(currentPassword: String, newPassword: String) {
        api.changePassword(
            ChangePasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword
            ),
            tokenManager.getToken()
        )
    }

    fun saveNotificationSettings(notificationsEnabled: Boolean, soundEnabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(PREFS_NOTIFICATIONS, notificationsEnabled)
            .putBoolean(PREFS_SOUND, soundEnabled)
            .apply()
    }

    fun getNotificationSettings(): Pair<Boolean, Boolean> {
        return Pair(
            sharedPreferences.getBoolean(PREFS_NOTIFICATIONS, true),
            sharedPreferences.getBoolean(PREFS_SOUND, true)
        )
    }
}