package ru.netology.tco2rus.data

data class HistoryOrder(
    val id: Long,
    val orderNumber: String,
    val fromAddress: String,
    val toAddress: String,
    val clientName: String,
    val cargoType: String,
    val cargoWeight: Double,
    val loadingDate: String,
    val unloadingDate: String,
    val travelTime: String,
    val price: Double,
    val status: String,
    val documents: List<String>
)
