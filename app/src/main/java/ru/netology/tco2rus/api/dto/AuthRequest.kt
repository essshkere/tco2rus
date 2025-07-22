package ru.netology.tco2rus.api.dto

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String
)