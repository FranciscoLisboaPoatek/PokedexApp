package com.example.pokedexapp.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen() {
    abstract val route: String
    data object PokemonListScreen: Screen(){
        override val route = "pokemon_list_screen"
    }
    data object PokemonDetailScreen: Screen(){
        override val route = "pokemon_detail_screen"
        val pokemonIdArg = "pokemon_id"
        val routeWithArgs = "$route/{$pokemonIdArg}"
        val arguments = listOf(
            navArgument("pokemon_id"){
                type = NavType.IntType
            }
        )
    }
}