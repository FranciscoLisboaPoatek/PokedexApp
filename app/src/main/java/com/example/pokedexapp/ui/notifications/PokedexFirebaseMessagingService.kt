package com.example.pokedexapp.ui.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PokedexFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.data["notification_type"].let { notificationType ->
            when (notificationType) {
                "share_pokemon" -> {
                    message.notification?.let { notification ->
                        sendSharePokemonNotification(
                            notification = notification,
                            data = message.data,
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun sendSharePokemonNotification(
        notification: RemoteMessage.Notification,
        data: Map<String, String>,
    ) {
        val title = notification.title
        val body = notification.body
        val deeplink = data["deeplink"]
        if (deeplink != null && title != null && body != null) {
            SharePokemonNotification(this)
                .showNotification(
                    deeplink = deeplink,
                    title = title,
                    body = body,
                )
        }
    }
}
