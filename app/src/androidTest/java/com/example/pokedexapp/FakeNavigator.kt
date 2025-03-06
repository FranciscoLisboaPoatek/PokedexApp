package com.example.pokedexapp

import android.net.Uri
import com.example.pokedexapp.ui.navigation.NavigationEvent
import com.example.pokedexapp.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow

class FakeNavigator : Navigator {
    override val state = MutableStateFlow<NavigationEvent>(NavigationEvent.Empty)

    override fun navigateTo(route: String) { }

    override fun handleDeeplink(uri: Uri) { }

    override fun navigateUp() { }

    override fun consumeEvent() { }
}
