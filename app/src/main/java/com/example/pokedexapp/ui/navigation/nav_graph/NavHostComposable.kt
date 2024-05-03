package com.example.pokedexapp.ui.navigation.nav_graph

import PokemonDetailScreenNavGraphComposable
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.utils.DEEPLINK_URI_SCHEME

@Composable
fun NavHostComposable(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.PokemonListScreen.route) {
        composable(
            route = Screen.PokemonListScreen.route,
        ) {
            PokemonListScreenNavGraphComposable()
        }

        composable(
            route = Screen.PokemonDetailScreen.routeWithArgs,
            deepLinks =
                listOf(
                    navDeepLink {
                        uriPattern = DEEPLINK_URI_SCHEME.plus(Screen.PokemonDetailScreen.routeWithArgs)
                    },
                ),
            arguments = Screen.PokemonDetailScreen.arguments,
        ) {
            PokemonDetailScreenNavGraphComposable()
        }
    }
}
