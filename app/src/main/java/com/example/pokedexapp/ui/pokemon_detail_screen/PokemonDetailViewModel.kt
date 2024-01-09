package com.example.pokedexapp.ui.pokemon_detail_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: Int = checkNotNull(savedStateHandle["pokemon_id"])
    private val _state = MutableStateFlow( PokemonDetailScreenUiState() )

    init {
        Log.w("nav","$pokemonId")
        viewModelScope.launch {
            _state.updateState { copy(pokemonModel = PokemonSampleData.singlePokemonSampleData()) }
            _state.updateState { copy(isLoading = false, isError = false,pokemonSprite = pokemonModel?.frontDefaultSprite) }
        }
    }
    val state get() = _state
    fun changeShinyPokemonSprite(actualPokemonSprite: SpriteType){
        val sprite = when(actualPokemonSprite){
            SpriteType.FRONT_DEFAULT -> state.value.pokemonModel?.frontShinySprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonModel?.frontDefaultSprite
            SpriteType.BACK_DEFAULT -> state.value.pokemonModel?.backShinySprite
            SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonModel?.backDefaultSprite
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }

    fun rotatePokemonSprite(actualPokemonSprite: SpriteType){
        val sprite = when(actualPokemonSprite){
            SpriteType.FRONT_DEFAULT -> state.value.pokemonModel?.backDefaultSprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonModel?.backShinySprite
            SpriteType.BACK_DEFAULT -> state.value.pokemonModel?.frontDefaultSprite
            SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonModel?.frontShinySprite
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }
}