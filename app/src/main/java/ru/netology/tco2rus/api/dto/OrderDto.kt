package ru.netology.tco2rus.api.dto

data class OrderDto(
    val id: Long,
    val status: String,
    val fromAddress: String,
    val toAddress: String,
    val clientName: String?,
    val cargoType: String?,
    val cargoWeight: Double?,
    val loadingDate: String?,
    val estimatedTime: String?,
    val price: Double?,
    val fromLat: Double?,
    val fromLon: Double?,
    val toLat: Double?,
    val toLon: Double?
)