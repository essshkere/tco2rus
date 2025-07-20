package ru.netology.tco2rus.api.dto

import com.google.gson.annotations.SerializedName

data class HistoryOrderDto(
    @SerializedName("id") val id: Long,
    @SerializedName("orderNumber") val orderNumber: String,
    @SerializedName("fromAddress") val fromAddress: String,
    @SerializedName("toAddress") val toAddress: String,
    @SerializedName("clientName") val clientName: String,
    @SerializedName("cargoType") val cargoType: String,
    @SerializedName("cargoWeight") val cargoWeight: Double,
    @SerializedName("loadingDate") val loadingDate: String,
    @SerializedName("unloadingDate") val unloadingDate: String,
    @SerializedName("travelTime") val travelTime: String,
    @SerializedName("price") val price: Double,
    @SerializedName("status") val status: String,
    @SerializedName("documents") val documents: List<String>?
)
