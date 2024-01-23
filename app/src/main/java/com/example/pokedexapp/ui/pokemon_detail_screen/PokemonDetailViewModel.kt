package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.POKEMON_ID_KEY
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: String = checkNotNull(savedStateHandle[POKEMON_ID_KEY])
    private val _state = MutableStateFlow( PokemonDetailScreenUiState() )
    val state get() = _state

    init {
        viewModelScope.launch {
            _state.updateState {
                val pokemon = PokemonSampleData.singlePokemonSampleData()
                copy(
                    pokemonModel = pokemon,
                    isLoading = false,
                    isError = false,
                    pokemonSprite = pokemon.frontDefaultSprite
                )
            }

        }
    }

    fun changeShinyPokemonSprite(actualPokemonSprite: SpriteType) {
        val sprite = when (actualPokemonSprite) {
            SpriteType.FRONT_DEFAULT -> state.value.pokemonModel?.frontShinySprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonModel?.frontDefaultSprite
            SpriteType.BACK_DEFAULT -> state.value.pokemonModel?.backShinySprite
            SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonModel?.backDefaultSprite
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }

    fun rotatePokemonSprite(actualPokemonSprite: SpriteType) {
        val sprite = when (actualPokemonSprite) {
            SpriteType.FRONT_DEFAULT -> state.value.pokemonModel?.backDefaultSprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonModel?.backShinySprite
            SpriteType.BACK_DEFAULT -> state.value.pokemonModel?.frontDefaultSprite
            SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonModel?.frontShinySprite
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }
}