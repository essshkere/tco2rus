package ru.netology.tco2rus.api.dto

import com.google.gson.annotations.SerializedName

data class HistoryOrderDto(
    @SerializedName("id") val id: Long,
    @SerializedName("status") val status: String,
)