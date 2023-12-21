package com.example.pokedexapp.ui.pokemon_list_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.components.PokemonTopAppBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    fun onEvent(event: PokemonListScreenOnEvent) {
        when (event) {
            is PokemonListScreenOnEvent.OnSearchClick -> {
                viewModel.changeIsSearchMode()
            }

            is PokemonListScreenOnEvent.OnSearchTextValueChange -> {
                viewModel.searchPokemonByName(event.text)
            }

            is PokemonListScreenOnEvent.OnPokemonCLick -> {
                //TODO
            }
        }
    }

    Scaffold(
        topBar = {
            PokemonTopAppBar(
                searchMode = state.isSearchMode,
                onEvent = ::onEvent
            )
        }
    ) {
        PokemonListScreenContent(pokemonList = state.pokemonList, modifier = Modifier.padding(it))
    }
}

@Composable
fun PokemonListScreenContent(pokemonList: List<PokemonModel>, modifier: Modifier) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(pokemonList) { pokemon ->
                PokemonListItem(pokemon = pokemon, strokeWidthDp = 10)
            }
        }
    }
}