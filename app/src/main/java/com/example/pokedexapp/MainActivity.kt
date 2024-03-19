package com.example.pokedexapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.pokedexapp.ui.Screen
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonDetailScreen
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreen
import com.example.pokedexapp.ui.theme.PokedexAppTheme
import com.example.pokedexapp.ui.utils.DEEPLINK_URI_SCHEME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    PokedexApp()

                    //Button to test the notification
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(onClick = { showNotification("${(1..1000).random()}") }) {
                            Text(text = "Show Notification")
                        }
                    }
                }
            }
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val uri = intent?.data
        if (uri != null) {
            viewModel.handleDeepLink(uri)
        }
    }

    private fun showNotification(pokemonId: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "$DEEPLINK_URI_SCHEME${Screen.PokemonDetailScreen.route}/$pokemonId".toUri(),
            applicationContext,
            this::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, NotificationChannels.DAILY.channelId)
            .setSmallIcon(R.drawable.baseline_catching_pokemon_24)
            .setContentTitle(getString(R.string.daily_pokemon_notification_title))
            .setContentText(getString(R.string.daily_pokemon_notification_text, pokemonId))
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(
            1,
            notification
        )
    }
}

@Composable
fun PokedexApp() {

    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = state.event) {
        when (state.event) {
            is Event.NavigateWithDeeplink -> navController.navigate((state.event as Event.NavigateWithDeeplink).deeplink)
            null -> {}
        }

        viewModel.consumeEvent()
    }

    NavHost(navController = navController, startDestination = Screen.PokemonListScreen.route) {

        composable(route = Screen.PokemonListScreen.route) {
            PokemonListScreen { navController.navigateToPokemonDetail(it) }
        }
        composable(
            route = Screen.PokemonDetailScreen.routeWithArgs,
            deepLinks = listOf(navDeepLink {
                uriPattern = DEEPLINK_URI_SCHEME.plus(Screen.PokemonDetailScreen.routeWithArgs)
            }),
            arguments = Screen.PokemonDetailScreen.arguments
        ) { backStackEntry ->
            PokemonDetailScreen(navigateUp = { navController.navigateUp() })
        }

    }

}

private fun NavController.navigateToPokemonDetail(pokemonId: String) {
    this.navigate("${Screen.PokemonDetailScreen.route}/$pokemonId")
}


