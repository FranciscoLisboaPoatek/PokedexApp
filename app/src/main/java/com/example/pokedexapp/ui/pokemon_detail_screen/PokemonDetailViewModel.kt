package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.lifecycle.ViewModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow( PokemonDetailScreenUiState() )

    init {
        _state.updateState { copy(pokemonModel = PokemonSampleData.singlePokemonSampleData()) }
        _state.updateState { copy(pokemonSprite = pokemonModel?.frontDefaultSprite) }
    }
    val state get() = _state
    fun changePokemonSprite(actualPokemonSprite: SpriteType){
        val sprite = when(actualPokemonSprite){
            SpriteType.FRONT_DEFAULT -> state.value.pokemonModel?.frontShinySprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonModel?.frontDefaultSprite
            SpriteType.BACK_DEFAULT -> TODO()
            SpriteType.BACK_SHINY_DEFAULT -> TODO()
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }

}