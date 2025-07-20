package ru.netology.tco2rus.api.dto

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
