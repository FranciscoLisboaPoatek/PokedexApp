package com.example.pokedexapp.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.pokedexapp.MainActivity
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.utils.INTENT_EXTRA_DEEPLINK_KEY
import com.example.pokedexapp.ui.utils.SHARE_POKEMON_NOTIFICATION_ID_KEY

class SharePokemonNotification(
    val context: Context,
) {
    fun showNotification(
        deeplink: String,
        title: String,
        body: String,
    ) {
        val intent =
            Intent(
                context,
                MainActivity::class.java,
            ).putExtra(INTENT_EXTRA_DEEPLINK_KEY, deeplink)

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                SHARE_POKEMON_NOTIFICATION_ID_KEY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        val notification =
            NotificationCompat.Builder(context, NotificationChannels.SHARE_POKEMON.channelId)
                .setSmallIcon(R.drawable.baseline_catching_pokemon_24)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(
            SHARE_POKEMON_NOTIFICATION_ID_KEY,
            notification,
        )
    }
}
