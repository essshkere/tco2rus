package ru.netology.tco2rus.repository

import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.dto.HistoryOrderDto
import ru.netology.tco2rus.api.dto.SupportMessageRequest
import ru.netology.tco2rus.data.HistoryOrder
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
                userId = tokenManager.getUserId()
            )
        )
    }

    suspend fun logCall() {
        api.logSupportCall(tokenManager.getUserId())
    }
}
