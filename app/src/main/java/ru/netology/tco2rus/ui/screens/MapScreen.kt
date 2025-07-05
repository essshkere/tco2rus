package ru.netology.tco2rus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView

@Composable
fun MapScreen() {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                map.mapType = MapType.NORMAL
                map.move(CameraPosition(Point(55.751244, 37.618423), 10f, 0f, 0f)
            }
        }
    )
}