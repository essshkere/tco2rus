package ru.netology.tco2rus.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import ru.netology.tco2rus.api.dto.*

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val userId: Long
)

interface ApiService {

    @GET("orders")
    suspend fun getActiveOrders(
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    ): List<OrderDto>

    @GET("history")
    suspend fun getHistory(
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    ): List<HistoryOrderDto>

    @POST("orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") orderId: Long,
        @Body status: OrderStatusRequest,
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    )

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    ): ProfileDto

    @GET("orders")
    suspend fun getActiveOrders(): List<OrderDto>

    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

}