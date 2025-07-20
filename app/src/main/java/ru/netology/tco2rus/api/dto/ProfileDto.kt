package ru.netology.tco2rus.api.dto

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,

    )