package ru.netology.tco2rus.data

import ru.netology.tco2rus.api.dto.OrderDto

object OrderMapper {
    fun fromDto(dto: OrderDto): Order {
        return Order(
            id = dto.id,
            status = OrderStatus.valueOf(dto.status),
            fromAddress = dto.fromAddress ?: "Не указано",
            toAddress = dto.toAddress ?: "Не указано",
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