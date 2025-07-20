package ru.netology.tco2rus.api.dto

import com.google.gson.annotations.SerializedName

data class DriverProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String?,
    @SerializedName("columnNumber") val columnNumber: String,
    @SerializedName("columnPhone") val columnPhone: String,
    @SerializedName("logistPhone") val logistPhone: String,
    @SerializedName("avatarUrl") val avatarUrl: String?
)
