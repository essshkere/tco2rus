package ru.netology.tco2rus.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.netology.tco2rus.api.dto.*
import ru.netology.tco2rus.api.dto.SupportMessageRequest

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("auth/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest,
        @Header("Authorization") token: String
    )

    @POST("support/message")
    suspend fun sendSupportMessage(
        @Body request: SupportMessageRequest,
        @Header("Authorization") token: String
    )

    @POST("support/call")
    suspend fun logSupportCall(
        @Header("Authorization") token: String
    )

    @PUT("profile")
    suspend fun updateProfile(
        @Body profile: DriverProfileDto,
        @Header("Authorization") token: String
    )

    @GET("profile")
    suspend fun getDriverProfile(
        @Header("Authorization") token: String
    ): DriverProfileDto

    @GET("history")
    suspend fun getHistoryOrders(
        @Header("Authorization") token: String
    ): List<HistoryOrderDto>

    @GET("history")
    suspend fun getHistory(
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    ): List<HistoryOrderDto>

    @GET("orders/{id}")
    suspend fun getOrderDetails(
        @Path("id") orderId: Long,
        @Header("Authorization") token: String
    ): OrderDto

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") apiKey: String = "c1378193-bc0e-42c8-a502-b8d66d189617"
    ): ProfileDto

    @GET("orders")
    suspend fun getActiveOrders(): List<OrderDto>

    @GET("orders/active")
    suspend fun getActiveOrders(
        @Header("Authorization") token: String
    ): List<OrderDto>

    @POST("orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") orderId: Long,
        @Body status: OrderStatusRequest,
        @Header("Authorization") token: String
    )
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse
}

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)