package ru.netology.tco2rus.api.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)

data class RegisterResponse(
    val token: String
)
