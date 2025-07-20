package ru.netology.tco2rus.data

data class Order(
    val id: Long,
    val status: OrderStatus,
    val fromAddress: String,
    val toAddress: String,
    val clientName: String,
    val cargoType: String,
    val cargoWeight: String,
    val loadingDate: String,
    val deliveryDate: String,
    val price: String,
    val fromLat: Double,
    val fromLon: Double,
    val toLat: Double,
    val toLon: Double
) {
    fun getStatusText(): String {
        return when (status) {
            OrderStatus.NEW -> "Новый"
            OrderStatus.ACCEPTED -> "Принят"
            OrderStatus.LOADING -> "Погрузка"
            OrderStatus.ON_WAY -> "В пути"
            OrderStatus.UNLOADING -> "Разгрузка"
            OrderStatus.DELIVERED -> "Доставлен"
            OrderStatus.COMPLETED -> "Завершен"
        }
    }
}

sealed class OrderStatusChangeResult {
    data class Success(val order: Order) : OrderStatusChangeResult()
    data class Error(val errorMessage: String) : OrderStatusChangeResult()
}
