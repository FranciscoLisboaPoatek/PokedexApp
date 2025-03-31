package com.example.pokedexapp.ui.navigation.nav_graph

import PokemonDetailScreenNavGraphComposable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.pokedexapp.ui.maps_screen.MapsScreen
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.navigation.topLevelRoutes
import com.example.pokedexapp.ui.theme.SurfaceColorDark
import com.example.pokedexapp.ui.theme.SurfaceColorLight
import com.example.pokedexapp.ui.theme.TopBarBlueColor
import com.example.pokedexapp.ui.utils.DEEPLINK_URI_SCHEME

@Composable
fun NavHostComposable(navController: NavHostController) {
    var currentSelectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val density = LocalDensity.current

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .drawWithContent {
                        drawContent()
                        drawLine(
                            color = TopBarBlueColor,
                            start = Offset.Zero,
                            end = Offset(this.size.width, 0f),
                            strokeWidth = with(density) { 2.dp.toPx() },
                            alpha = 1f
                        )
                    },
                containerColor = if (isSystemInDarkTheme()) SurfaceColorDark else SurfaceColorLight,
            ) {
                topLevelRoutes.forEachIndexed { index, routes ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(routes.iconId),
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = stringResource(routes.nameId))
                        },
                        onClick = {
                            currentSelectedItemIndex = index
                            navController.navigate(routes.route.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        },
                        selected = index == currentSelectedItemIndex,
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIconColor = TopBarBlueColor,
                            selectedTextColor = TopBarBlueColor
                        )
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.PokemonListScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = Screen.PokemonListScreen.route,
            ) {
                PokemonListScreenNavGraphComposable()
            }

            composable(
                route = Screen.MapsScreen.route
            ) {
                MapsScreen()
            }

            composable(
                route = Screen.PokemonDetailScreen.routeWithArgs,
                deepLinks =
                    listOf(
                        navDeepLink {
                            uriPattern =
                                DEEPLINK_URI_SCHEME.plus(Screen.PokemonDetailScreen.routeWithArgs)
                        },
                    ),
                arguments = Screen.PokemonDetailScreen.arguments,
            ) {
                PokemonDetailScreenNavGraphComposable()
            }
        }
    }
}
