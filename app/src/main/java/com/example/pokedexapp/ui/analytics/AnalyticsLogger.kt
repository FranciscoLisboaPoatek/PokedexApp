package com.example.pokedexapp.ui.analytics

interface AnalyticsLogger {
    fun logEvent(
        name: String,
        parameters: Map<String, String>,
    )
}
