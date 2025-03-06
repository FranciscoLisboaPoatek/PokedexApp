package com.example.pokedexapp.ui.navigation

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

interface Navigator {
    val state: MutableStateFlow<NavigationEvent>

    fun navigateTo(route: String)

    fun handleDeeplink(uri: Uri)

    fun navigateUp()

    fun consumeEvent()
}

sealed class NavigationEvent() {
    data class NavigateWithDeeplink(val deeplink: Uri) : NavigationEvent()

    data class NavigateToScreen(val route: String) : NavigationEvent()

    data object NavigateUp : NavigationEvent()

    data object Empty : NavigationEvent()
}
