package ru.netology.tco2rus.repository

import android.content.SharedPreferences
import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.dto.HistoryOrderDto
import ru.netology.tco2rus.api.dto.RegisterRequest
import ru.netology.tco2rus.data.HistoryOrder
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun register(request: RegisterRequest) {
        val response = api.register(request)
        tokenManager.saveToken(response.token)
    }
}