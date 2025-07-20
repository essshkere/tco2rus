package ru.netology.tco2rus.data

data class DriverProfile(
    val id: Long,
    val fullName: String,
    val phone: String,
    val email: String,
    val columnNumber: String,
    val columnPhone: String,
    val logistPhone: String,
    val avatarUrl: String
)
