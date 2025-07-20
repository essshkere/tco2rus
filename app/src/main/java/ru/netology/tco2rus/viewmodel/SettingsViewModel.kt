package ru.netology.tco2rus.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.netology.tco2rus.data.HistoryOrder
import ru.netology.tco2rus.repository.AuthRepository
import ru.netology.tco2rus.repository.HistoryRepository
import ru.netology.tco2rus.repository.SettingsRepository
import ru.netology.tco2rus.repository.TokenManager
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isPasswordChanged = MutableStateFlow(false)
    val isPasswordChanged: StateFlow<Boolean> = _isPasswordChanged

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authRepository.changePassword(currentPassword, newPassword)
                _isPasswordChanged.value = true
            } catch (e: Exception) {
                _error.value = "Ошибка смены пароля: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            _isLoggedOut.value = true
        }
    }

    fun saveNotificationSettings(notificationsEnabled: Boolean, soundEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveNotificationSettings(notificationsEnabled, soundEnabled)
        }
    }

    fun loadNotificationSettings(): Pair<Boolean, Boolean> {
        return settingsRepository.getNotificationSettings()
    }

    fun clearError() {
        _error.value = null
    }

    fun resetPasswordChanged() {
        _isPasswordChanged.value = false
    }

    fun resetLoggedOut() {
        _isLoggedOut.value = false
    }
}