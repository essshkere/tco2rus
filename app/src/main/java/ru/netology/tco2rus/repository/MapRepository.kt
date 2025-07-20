package ru.netology.tco2rus.repository

import com.yandex.mapkit.geometry.Point
import javax.inject.Inject

class MapRepository @Inject constructor() {
    suspend fun getRoutePoints(
        fromLat: Double,
        fromLon: Double,
        toLat: Double,
        toLon: Double
    ): List<Point> {
        return listOf(
            Point(fromLat, fromLon),
            Point(toLat, toLon)
        )
    }
}
