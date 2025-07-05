package ru.netology.tco2rus.api

import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    ): List<OrderDto>
}