package com.example.pokedexapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.pokedexapp.ui.Screen
import com.example.pokedexapp.ui.utils.DEEPLINK_URI_SCHEME

class DailyPokemonNotification(
    val context: Context
) {
    fun showNotification(pokemonId: String, pokemonName: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "$DEEPLINK_URI_SCHEME${Screen.PokemonDetailScreen.route}/$pokemonId".toUri(),
            context,
            MainActivity::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, NotificationChannels.DAILY.channelId)
            .setSmallIcon(R.drawable.baseline_catching_pokemon_24)
            .setContentTitle(context.getString(R.string.daily_pokemon_notification_title))
            .setContentText(context.getString(R.string.daily_pokemon_notification_text, pokemonName))
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(
            1,
            notification
        )
    }
}