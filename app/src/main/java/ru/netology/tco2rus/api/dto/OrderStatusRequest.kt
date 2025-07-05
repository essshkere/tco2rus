package ru.netology.tco2rus.api.dto

import ru.netology.tco2rus.data.OrderStatus

data class OrderStatusRequest(
    val status: OrderStatus
)