package com.example.pokedexapp.ui

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

interface Navigator {
    val state: MutableStateFlow<NavigationEvent>
    fun navigateTo(screen: Screen)

    sealed class NavigationEvent() {
        data class NavigateWithDeeplink(val deeplink: Uri) : NavigationEvent()
        data class NavigateToScreen(val route: Uri) : NavigationEvent()
    }
}