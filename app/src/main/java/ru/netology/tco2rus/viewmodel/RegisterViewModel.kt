package ru.netology.tco2rus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.netology.tco2rus.api.dto.RegisterRequest
import ru.netology.tco2rus.repository.AuthRepository
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                authRepository.register(
                    RegisterRequest(
                        name = name,
                        email = email,
                        phone = phone,
                        password = password
                    )
                )
                _registrationState.value = RegistrationState.Success
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error(e.message ?: "Ошибка регистрации")
            }
        }
    }

    sealed class RegistrationState {
        object Idle : RegistrationState()
        object Loading : RegistrationState()
        object Success : RegistrationState()
        data class Error(val message: String) : RegistrationState()
    }
}