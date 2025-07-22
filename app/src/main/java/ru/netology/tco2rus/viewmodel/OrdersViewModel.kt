package ru.netology.tco2rus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.data.Order
import ru.netology.tco2rus.api.dto.OrderStatusRequest
import ru.netology.tco2rus.data.OrderMapper
import ru.netology.tco2rus.data.OrderStatus
import ru.netology.tco2rus.repository.TokenManager
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _activeOrders = MutableStateFlow<List<Order>>(emptyList())
    val activeOrders: StateFlow<List<Order>> = _activeOrders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val ordersDto = api.getActiveOrders(tokenManager.getToken())
                _activeOrders.value = ordersDto.map { OrderMapper.fromDto(it) }
            } catch (e: Exception) {
                _error.value = "Failed to load orders: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateStatus(orderId: Long, status: OrderStatus) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                api.updateOrderStatus(
                    orderId,
                    OrderStatusRequest(status),
                    tokenManager.getToken()
                )
                loadOrders()
            } catch (e: Exception) {
                _error.value = "Failed to update status: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
