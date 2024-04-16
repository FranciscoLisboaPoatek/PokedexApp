package com.example.pokedexapp.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

fun createNotificationChannels(notificationManager: NotificationManager) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createChannel(
            notificationManager,
            NotificationChannels.DAILY.channelId,
            NotificationChannels.DAILY.channelName,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        createChannel(
            notificationManager,
            NotificationChannels.SHARE_POKEMON.channelId,
            NotificationChannels.SHARE_POKEMON.channelName,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun createChannel(
    notificationManager: NotificationManager,
    channelId: String,
    channelName: String,
    channelImportance: Int,
) {
    val channel =
        NotificationChannel(
            channelId,
            channelName,
            channelImportance,
        )
    notificationManager.createNotificationChannel(channel)
}

enum class NotificationChannels(val channelId: String, val channelName: String) {
    DAILY(channelId = "channel_daily", channelName = "Daily pokemons"),
    SHARE_POKEMON(channelId = "channel_share_pokemon", channelName = "Shared pokemons"),
}
