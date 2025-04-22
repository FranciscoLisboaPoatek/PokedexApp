package com.example.pokedexapp.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pokedexapp.ui.utils.DEEPLINK_URI_SCHEME
import com.example.pokedexapp.ui.utils.POKEMON_ID_KEY

sealed class Screen(val route: String) {
    data object PokemonListScreen : Screen(ScreenRoute.LIST_SCREEN.route)

    data object PokemonDetailScreen : Screen(ScreenRoute.DETAIL_SCREEN.route) {
        val routeWithArgs = "$route/{$POKEMON_ID_KEY}"
        val arguments =
            listOf(
                navArgument(POKEMON_ID_KEY) {
                    type = NavType.StringType
                },
            )

        fun makeDeeplink(pokemonId: String): String = "$DEEPLINK_URI_SCHEME${this.route}/$pokemonId"

        fun navigateToPokemonDetail(pokemonId: String): String {
            return "${PokemonDetailScreen.route}/$pokemonId"
        }
    }

    data object ChoosePokemonWidgetScreen : Screen(ScreenRoute.CHOOSE_POKEMON_WIDGET_SCREEN.route) {
        val routeWithArgs = "$route/{$POKEMON_ID_KEY}"
        val arguments =
            listOf(
                navArgument(POKEMON_ID_KEY) {
                    type = NavType.StringType
                },
            )

        fun navigateToChoosePokemonWidget(pokemonId: String): String {
            return "${ChoosePokemonWidgetScreen.route}/$pokemonId"
        }
    }
}

enum class ScreenRoute(val route: String) {
    LIST_SCREEN("pokemon_list_screen"),
    DETAIL_SCREEN("pokemon_detail_screen"),
    CHOOSE_POKEMON_WIDGET_SCREEN("choose_pokemon_widget_screen"),
}
