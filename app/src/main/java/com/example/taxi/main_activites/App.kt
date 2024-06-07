package com.example.taxi.main_activites

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import com.yandex.maps.mobile.BuildConfig

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("52eb0455-d7e5-4526-a6e4-ca5699d8a4bc")
    }
}