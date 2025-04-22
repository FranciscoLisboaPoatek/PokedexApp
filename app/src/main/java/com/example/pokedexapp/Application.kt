package com.example.pokedexapp

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.pokedexapp.ui.notifications.EnqueueDailyNotificationWorker
import com.example.pokedexapp.ui.notifications.createNotificationChannels
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application(), Configuration.Provider {
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannels(notificationManager)

        EnqueueDailyNotificationWorker().enqueue(this)
    }

    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(hiltWorkerFactory)
                .build()
}
