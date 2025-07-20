package ru.netology.tco2rus.api.dto

data class SupportMessageRequest(
    val subject: String,
    val message: String,
    val userId: String
)
