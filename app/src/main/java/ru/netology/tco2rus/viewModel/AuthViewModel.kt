package ru.netology.tco2rus.viewModel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.AuthRequest
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: ApiService,
    private val prefs: SharedPreferences
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val token: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = api.login(AuthRequest(email, password))
                prefs.edit { putString("token", response.token) }
                _authState.value = AuthState.Success(response.token)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun getToken(): String? = prefs.getString("token", null)

}