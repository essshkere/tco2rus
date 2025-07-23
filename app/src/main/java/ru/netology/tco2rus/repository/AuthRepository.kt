package ru.netology.tco2rus.repository

import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.ChangePasswordRequest
import ru.netology.tco2rus.api.dto.AuthRequest
import ru.netology.tco2rus.api.dto.RegisterRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String) {
        val response = api.login(AuthRequest(email, password))
        tokenManager.saveToken(response.token)
    }

    suspend fun changePassword(currentPassword: String, newPassword: String) {
        api.changePassword(
            ChangePasswordRequest(currentPassword, newPassword),
            tokenManager.getToken()
        )
    }
    suspend fun register(request: RegisterRequest) {
        val response = api.register(request)
        tokenManager.saveToken(response.token)
    }
}