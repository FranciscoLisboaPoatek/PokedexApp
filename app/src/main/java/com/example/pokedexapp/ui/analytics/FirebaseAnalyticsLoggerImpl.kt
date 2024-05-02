package com.example.pokedexapp.ui.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsLoggerImpl
    @Inject
    constructor(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsLogger {
        override fun logEvent(
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
