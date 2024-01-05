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
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png")
            )
        )
    )

    val state get() = _state
    fun changePokemonSprite(actualPokemonSprite: SpriteType){
        val sprite = when(actualPokemonSprite){
            SpriteType.FRONT_DEFAULT -> state.value.pokemonModel.frontShinySprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonModel.frontDefaultSprite
            SpriteType.BACK_DEFAULT -> TODO()
            SpriteType.BACK_SHINY_DEFAULT -> TODO()
        }
        _state.value = _state.value.copy(pokemonSprite = sprite)
    }

}