package ru.netology.tco2rus.data

enum class OrderStatus {
    NEW, IN_PROGRESS, COMPLETED, CANCELLED;

    companion object {
        fun fromString(value: String): OrderStatus {
            return valueOf(value.uppercase())
        }
    }
}