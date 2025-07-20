package ru.netology.tco2rus.data

import ru.netology.tco2rus.api.dto.OrderDto

object OrderMapper {
    fun fromDto(dto: OrderDto): Order {
        return Order(
            id = dto.id,
            status = OrderStatus.valueOf(dto.status),
            title = dto.title,
            description = dto.description

        )
    }
}
