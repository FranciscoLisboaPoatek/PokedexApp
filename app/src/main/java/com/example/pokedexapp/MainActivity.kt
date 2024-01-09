package com.example.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.ui.Screen
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonDetailScreen
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreen
import com.example.pokedexapp.ui.theme.PokedexAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexAppTheme {
                PokedexApp()
            }
        }
    }
}

@Composable
fun PokedexApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PokemonListScreen.route){

        composable(route = Screen.PokemonListScreen.route){
            PokemonListScreen{ navController.navigateToPokemonDetail(it) }
        }
        composable(
            route = Screen.PokemonDetailScreen.routeWithArgs,
            arguments = Screen.PokemonDetailScreen.arguments
        ){
            PokemonDetailScreen(navigateUp = { navController.navigateUp() })
        }
    }
}

private fun NavController.navigateToPokemonDetail(pokemonId: Int){
    this.navigate("${ Screen.PokemonDetailScreen.route }/$pokemonId")
}


