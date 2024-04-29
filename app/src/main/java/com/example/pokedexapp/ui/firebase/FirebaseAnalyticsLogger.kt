package com.example.pokedexapp.ui.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsLogger
    @Inject
    constructor(private val firebaseAnalytics: FirebaseAnalytics) {
        fun logFirebaseEvent(
            name: String,
            parameters: Map<String, String>,
        ) {
            val bundle =
                Bundle().apply {
                    parameters.forEach { (key, value) ->
                        putString(key, value)
                    }
                }

            firebaseAnalytics.logEvent(name, bundle)
        }
    }
