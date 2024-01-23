package com.example.pokedexapp.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

const val POKEMON_ID_KEY = "pokemon_id"

sealed class Screen(val route: String) {
    data object PokemonListScreen: Screen(ScreenRoute.LIST_SCREEN.route)
    data object PokemonDetailScreen: Screen(ScreenRoute.DETAIL_SCREEN.route){
        val routeWithArgs = "$route/{$POKEMON_ID_KEY}"
        val arguments = listOf(
            navArgument(POKEMON_ID_KEY){
                type = NavType.StringType
            }
        )
    }
}

enum class ScreenRoute(val route: String){
    LIST_SCREEN("pokemon_list_screen"),
    DETAIL_SCREEN("pokemon_detail_screen")
}