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
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<HistoryOrder>>(emptyList())
    val orders: StateFlow<List<HistoryOrder>> = _orders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _orders.value = repository.getHistory()
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Error loading history", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
