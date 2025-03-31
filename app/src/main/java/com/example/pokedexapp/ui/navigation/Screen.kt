package com.example.pokedexapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pokedexapp.R
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

    data object MapsScreen : Screen(ScreenRoute.MAPS_SCREEN.route)
}

enum class ScreenRoute(val route: String) {
    LIST_SCREEN("pokemon_list_screen"),
    DETAIL_SCREEN("pokemon_detail_screen"),
    MAPS_SCREEN("maps_screen")
}

data class TopLevelRoute<T : Any>(@StringRes val nameId: Int, val route: T, @DrawableRes val iconId: Int)

val topLevelRoutes = listOf(
    TopLevelRoute(R.string.pokemons_bottom_navigation, ScreenRoute.LIST_SCREEN, R.drawable.baseline_catching_pokemon_24),
    TopLevelRoute(R.string.map_bottom_navigation, ScreenRoute.MAPS_SCREEN, R.drawable.baseline_map_24)
)