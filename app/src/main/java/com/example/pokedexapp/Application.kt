package com.example.pokedexapp

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.example.pokedexapp.ui.notifications.createNotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannels(notificationManager)
    }
}
