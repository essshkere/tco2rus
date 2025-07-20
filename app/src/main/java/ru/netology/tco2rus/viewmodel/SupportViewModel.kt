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
import ru.netology.tco2rus.repository.HistoryRepository
import ru.netology.tco2rus.repository.SupportRepository
import javax.inject.Inject


@HiltViewModel
class SupportViewModel @Inject constructor(
    private val repository: SupportRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isSent = MutableStateFlow(false)
    val isSent: StateFlow<Boolean> = _isSent

    fun sendMessage(subject: String, message: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.sendMessage(subject, message)
                _isSent.value = true
            } catch (e: Exception) {
                _error.value = "Ошибка отправки: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logCall() {
        viewModelScope.launch {
            repository.logCall()
        }
    }
}