package ru.netology.tco2rus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.netology.tco2rus.data.DriverProfile
import ru.netology.tco2rus.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<DriverProfile?>(null)
    val profile: StateFlow<DriverProfile?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _profile.value = repository.getProfile()
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки профиля: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(updatedProfile: DriverProfile) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateProfile(updatedProfile)
                _profile.value = updatedProfile
            } catch (e: Exception) {
                _error.value = "Ошибка обновления: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}