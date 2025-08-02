package ru.netology.tco2rus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.netology.tco2rus.data.Order
import ru.netology.tco2rus.data.OrderStatus
import ru.netology.tco2rus.repository.OrdersRepository
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val repository: OrdersRepository
) : ViewModel() {
    private val _order = MutableStateFlow<Order?>(null)
    val order: StateFlow<Order?> = _order

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _statusChangeResult = MutableStateFlow<OrderStatusChangeResult?>(null)
    val statusChangeResult: StateFlow<OrderStatusChangeResult?> = _statusChangeResult

    fun loadOrder(orderId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _order.value = repository.getOrderDetails(orderId)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun changeOrderStatus() {
        viewModelScope.launch {
            _order.value?.let { currentOrder ->
                _isLoading.value = true
                try {
                    val newStatus = getNextStatus(currentOrder.status)
                    repository.updateOrderStatus(currentOrder.id, newStatus)
                    _order.value = currentOrder.copy(status = newStatus)
                    _statusChangeResult.value = OrderStatusChangeResult.Success(currentOrder.copy(status = newStatus))
                } catch (e: Exception) {
                    _statusChangeResult.value = OrderStatusChangeResult.Error(e.message ?: "Unknown error")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    private fun getNextStatus(currentStatus: OrderStatus): OrderStatus {
        return when (currentStatus) {
            OrderStatus.NEW -> OrderStatus.ACCEPTED
            OrderStatus.ACCEPTED -> OrderStatus.LOADING
            OrderStatus.LOADING -> OrderStatus.ON_WAY
            OrderStatus.ON_WAY -> OrderStatus.UNLOADING
            OrderStatus.UNLOADING -> OrderStatus.DELIVERED
            OrderStatus.DELIVERED -> OrderStatus.COMPLETED
            OrderStatus.COMPLETED -> currentStatus
        }
    }
}

sealed class OrderStatusChangeResult {
    data class Success(val order: Order) : OrderStatusChangeResult()
    data class Error(val errorMessage: String) : OrderStatusChangeResult()
}
