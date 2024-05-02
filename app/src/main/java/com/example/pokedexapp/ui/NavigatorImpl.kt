package com.example.pokedexapp.ui

import kotlinx.coroutines.flow.MutableStateFlow

class NavigatorImpl: Navigator{

    override val state = MutableStateFlow<Navigator.NavigationEvent>()
    override fun navigateTo(screen: Screen) {
        TODO("Not yet implemented")
    }
}