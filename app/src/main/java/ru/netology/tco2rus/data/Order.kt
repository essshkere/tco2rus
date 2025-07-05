package ru.netology.tco2rus.data

data class Order(
    val id: Long,
    val status: OrderStatus,
    val title: String,
    val description: String,

)