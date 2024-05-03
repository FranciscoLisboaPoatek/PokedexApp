package com.example.pokedexapp.ui

import android.net.Uri
import com.example.pokedexapp.ui.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow

class NavigatorImpl : Navigator {

    override val state = MutableStateFlow<NavigationEvent>(NavigationEvent.Empty)

    override fun navigateTo(route: String) {
        state.updateState { NavigationEvent.NavigateToScreen(route) }
    }

    override fun handleDeeplink(uri: Uri) {
        state.updateState { NavigationEvent.NavigateWithDeeplink(uri) }
    }

    override fun navigateUp() {
        state.updateState { NavigationEvent.NavigateUp }
    }

    override fun consumeEvent() {
        state.updateState { NavigationEvent.Empty }
    }
}
