package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.lifecycle.ViewModel
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        PokemonDetailScreenUiState(
            pokemonModel = PokemonModel(
                1,
                "Bulbasaur",
                0.7f,
                6.9f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(149),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),
                PokemonTypes.GRASS,
                PokemonTypes.POISON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/265.png"
            )
        )
    )

    val state get() = _state
    fun changePokemonSprite(pokemonSprite: PokemonSprite){
        val sprite = when(pokemonSprite){
            PokemonSprite.FRONT_DEFAULT -> state.value.pokemonModel.frontDefaultImageUrl
            PokemonSprite.FRONT_SHINY_DEFAULT -> state.value.pokemonModel.frontShinyImageUrl
        }
        _state.value = _state.value.copy(pokemonSprite = sprite)
    }

}