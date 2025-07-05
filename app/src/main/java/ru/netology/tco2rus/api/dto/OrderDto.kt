package ru.netology.tco2rus.api.dto


data class OrderDto(
    val id: Long,
    val status: String,
    val title: String,
    val description: String
)