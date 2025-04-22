package com.example.pokedexapp.ui.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexapp.ui.choose_pokemon_widget.ChoosePokemonWidgetScreen
import com.example.pokedexapp.ui.choose_pokemon_widget.ChoosePokemonWidgetViewModel

@Composable
fun ChoosePokemonWidgetScreenNavGraphComposable() {
    val viewModel = hiltViewModel<ChoosePokemonWidgetViewModel>()
    val state by viewModel.state.collectAsState()
    ChoosePokemonWidgetScreen(
        state = state,
    ) {
        viewModel.onEvent(it)
    }
}
