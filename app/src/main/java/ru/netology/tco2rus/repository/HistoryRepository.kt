package ru.netology.tco2rus.repository

import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.dto.HistoryOrderDto
import ru.netology.tco2rus.data.HistoryOrder
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun getHistory(): List<HistoryOrder> {
        return api.getHistoryOrders(tokenManager.getToken())
            .map { it.toDomain() }
    }

    private fun HistoryOrderDto.toDomain(): HistoryOrder {
        return HistoryOrder(
            id = id,
            orderNumber = orderNumber,
            fromAddress = fromAddress,
            toAddress = toAddress,
            clientName = clientName,
            cargoType = cargoType,
            cargoWeight = cargoWeight,
            loadingDate = loadingDate,
            unloadingDate = unloadingDate,
            travelTime = travelTime,
            price = price,
            status = status,
            documents = documents ?: emptyList()
        )
    }
}
