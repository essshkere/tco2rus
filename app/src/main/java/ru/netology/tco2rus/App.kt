package ru.netology.tco2rus

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey("3acbd28e-158e-4f9e-b033-88b0f41418ed")
        MapKitFactory.initialize(this)
    }
}