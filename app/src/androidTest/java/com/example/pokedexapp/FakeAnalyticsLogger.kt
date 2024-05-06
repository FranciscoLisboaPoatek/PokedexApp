package com.example.pokedexapp

import com.example.pokedexapp.ui.analytics.AnalyticsLogger

class FakeAnalyticsLogger : AnalyticsLogger {
    override fun logEvent(
        name: String,
        parameters: Map<String, String>,
    ) {
    }
}
