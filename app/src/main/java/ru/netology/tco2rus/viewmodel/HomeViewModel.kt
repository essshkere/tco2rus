package ru.netology.tco2rus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.netology.tco2rus.repository.MapRepository
import ru.netology.tco2rus.repository.OrdersRepository
import javax.inject.Inject
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val mapRepository: MapRepository
) : ViewModel() {
    private val _routePoints = MutableLiveData<List<Point>>()
    val routePoints: LiveData<List<Point>> = _routePoints

    fun loadRoute(orderId: Long) {
        viewModelScope.launch {
            val order = ordersRepository.getOrderDetails(orderId)
            _routePoints.value = mapRepository.getRoutePoints(
                fromLat = order.fromLat,
                fromLon = order.fromLon,
                toLat = order.toLat,
                toLon = order.toLon
            )
        }
    }
}
