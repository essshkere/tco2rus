package ru.netology.tco2rus.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView

@Composable
fun MapScreen() {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            MapView(context).apply {

                map.mapType = MapType.VECTOR_MAP


                map.move(
                    CameraPosition(
                        Point(55.751244, 37.618423), // Москва
                        10f, // zoom
                        0f, // azimuth
                        0f  // tilt
                    )
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}