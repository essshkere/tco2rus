package ru.netology.tco2rus.repository

import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.dto.SupportMessageRequest
import javax.inject.Inject

class SupportRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun sendMessage(subject: String, message: String) {
        api.sendSupportMessage(
            SupportMessageRequest(
                subject = subject,
                message = message,
                userId = tokenManager.getToken()
            ),
            tokenManager.getToken()
        )
    }

    suspend fun logCall() {
        api.logSupportCall(
            tokenManager.getToken() 
        )
    }
}