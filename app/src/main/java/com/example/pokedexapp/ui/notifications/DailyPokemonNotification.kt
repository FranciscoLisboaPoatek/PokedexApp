package com.example.pokedexapp.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.pokedexapp.MainActivity
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.utils.DAILY_NOTIFICATION_ID_KEY
import com.example.pokedexapp.ui.utils.INTENT_EXTRA_DEEPLINK_KEY

class DailyPokemonNotification(
    val context: Context,
) {
    fun showNotification(
        pokemonId: String,
        pokemonName: String,
    ) {
        val intent =
            Intent(
                context,
                MainActivity::class.java,
            ).putExtra(INTENT_EXTRA_DEEPLINK_KEY, Screen.PokemonDetailScreen.makeDeeplink(pokemonId))

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                DAILY_NOTIFICATION_ID_KEY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        val notification =
            NotificationCompat.Builder(context, NotificationChannels.DAILY.channelId)
                .setSmallIcon(R.drawable.baseline_catching_pokemon_24)
                .setContentTitle(context.getString(R.string.daily_pokemon_notification_title))
                .setContentText(
                    context.getString(
                        R.string.daily_pokemon_notification_text,
                        pokemonName,
                    ),
                )
                .setContentIntent(pendingIntent)
                .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(
            DAILY_NOTIFICATION_ID_KEY,
            notification,
        )
    }
}
