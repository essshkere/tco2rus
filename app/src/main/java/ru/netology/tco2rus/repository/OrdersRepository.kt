package ru.netology.tco2rus.repository

import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.data.Order
import ru.netology.tco2rus.api.dto.OrderDto
import ru.netology.tco2rus.api.dto.OrderStatusRequest
import ru.netology.tco2rus.data.OrderStatus
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun getOrderDetails(orderId: Long): Order {
        val response = api.getOrderDetails(orderId, tokenManager.getToken())
        return mapOrderDtoToOrder(response)
    }

    suspend fun getActiveOrder(): Order {
        val response = api.getActiveOrders(tokenManager.getToken())
        return mapOrderDtoToOrder(response.first())
    }

    suspend fun updateOrderStatus(orderId: Long, status: OrderStatus) {
        api.updateOrderStatus(
            orderId,
            OrderStatusRequest(status),
            tokenManager.getToken()
        )
    }

    private fun mapOrderDtoToOrder(dto: OrderDto): Order {
        return Order(
            id = dto.id,
            status = OrderStatus.valueOf(dto.status),
            fromAddress = dto.fromAddress,
            toAddress = dto.toAddress,
            clientName = dto.clientName ?: "Не указано",
            cargoType = dto.cargoType ?: "Не указано",
            cargoWeight = dto.cargoWeight?.toString() ?: "0",
            loadingDate = dto.loadingDate ?: "Не указана",
            deliveryDate = dto.estimatedTime ?: "Не указан",
            price = dto.price?.toString() ?: "0",
            fromLat = dto.fromLat ?: 0.0,
            fromLon = dto.fromLon ?: 0.0,
            toLat = dto.toLat ?: 0.0,
            toLon = dto.toLon ?: 0.0
        )
    }
}
