package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(

) : ViewModel() {

    private var _state = MutableStateFlow(PokemonListScreenUiState())
    val state get() = _state

    private var pokemonList = listOf<PokemonModel>()

    init {
        pokemonList = sampleDataProvider()
        _state.value = _state.value.copy(pokemonList = pokemonList)
    }

    fun changeIsSearchMode() {
        _state.value = _state.value.copy(isSearchMode = !_state.value.isSearchMode)
    }

    fun searchPokemonByName(pokemonName: String) {
        if (pokemonName.isBlank()) {
            state.value = state.value.copy(pokemonList = pokemonList)
            return
        }
        viewModelScope.launch {
            state.value = state.value.copy(pokemonList = sampleSearchDataProvider())
        }
    }

    private fun sampleSearchDataProvider(): List<PokemonModel> = listOf(
        PokemonModel(
            1,
            "???",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),
            PokemonTypes.NORMAL,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png"
        ),
        PokemonModel(
            1,
            "???",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.NORMAL,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png"
        ),
    )

    private fun sampleDataProvider(): List<PokemonModel> = listOf(
        PokemonModel(
            1,
            "Ditto",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.NORMAL,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        PokemonModel(
            2,
            "Lucario",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.FIGHTING,
            PokemonTypes.STEEL,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png"
        ),
        PokemonModel(
            3,
            "Noivern",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.FLYING,
            PokemonTypes.DRAGON,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png"
        ),
        PokemonModel(
            4,
            "Crobat",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.POISON,
            PokemonTypes.FLYING,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png"
        ),
        PokemonModel(
            5,
            "Krookodile",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.GROUND,
            PokemonTypes.DARK,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png"
        ),
        PokemonModel(
            6,
            "Omanyte",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.ROCK,
            PokemonTypes.WATER,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png"
        ),
        PokemonModel(
            7,
            "Volcarona",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.BUG,
            PokemonTypes.FIRE,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png"
        ),
        PokemonModel(
            8,
            "Gengar",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.GHOST,
            PokemonTypes.POISON,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png"
        ),
        PokemonModel(
            9,
            "Jirachi",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.STEEL,
            PokemonTypes.PSYCHIC,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png"
        ),
        PokemonModel(
            10,
            "Ho-oh",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.FIRE,
            PokemonTypes.FLYING,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png"
        ),
        PokemonModel(
            11,
            "Blastoise",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.WATER,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"
        ),
        PokemonModel(
            12,
            "Roserade",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.GRASS,
            PokemonTypes.POISON,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png"
        ),
        PokemonModel(
            13,
            "Rotom",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.ELECTRIC,
            PokemonTypes.GHOST,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png"
        ),
        PokemonModel(
            14,
            "Hatterene",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.PSYCHIC,
            PokemonTypes.FAIRY,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png"
        ),
        PokemonModel(
            15,
            "Spheal",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.ICE,
            PokemonTypes.WATER,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png"
        ),
        PokemonModel(
            16,
            "Dragapult",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.DRAGON,
            PokemonTypes.GHOST,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png"
        ),
        PokemonModel(
            17,
            "Darkrai",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.DARK,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png"
        ),
        PokemonModel(
            18,
            "Floette",
            10.0f,
            10.0f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),

            PokemonTypes.FAIRY,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png"
        ),
    )


}