package com.example.pokedexapp.ui.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreen
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListViewModel

@Composable
fun PokemonListScreenNavGraphComposable() {
    val viewModel = hiltViewModel<PokemonListViewModel>()
    val state by viewModel.state.collectAsState()
    PokemonListScreen(
        state = state,
        onEvent = {
            viewModel.onEvent(it)
        },
    )
}
