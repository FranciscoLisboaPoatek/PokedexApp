package com.example.pokedexapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.pokedexapp.ui.NavigationEvent
import com.example.pokedexapp.ui.Navigator
import com.example.pokedexapp.ui.Screen
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonDetailScreen
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreen
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListViewModel
import com.example.pokedexapp.ui.theme.PokedexAppTheme
import com.example.pokedexapp.ui.utils.DEEPLINK_URI_SCHEME
import com.example.pokedexapp.ui.utils.INTENT_EXTRA_DEEPLINK_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokedexAppTheme(dynamicColor = false) {
                val context = LocalContext.current

                Box(modifier = Modifier.fillMaxSize()) {
                    PokedexApp(navigator)
                    RequestNotificationPermission(context = context)
                }
            }
            LaunchedEffect(key1 = Unit) {
                handleIntent(intent)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val deeplink = intent?.extras?.getString(INTENT_EXTRA_DEEPLINK_KEY)
        if (deeplink != null) {
            viewModel.handleDeepLink(deeplink.toUri())
        }
    }
}

@Composable
fun PokedexApp(navigator: Navigator) {
    val navController = rememberNavController()

    val state = navigator.state.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = state) {
        when (state) {
            is NavigationEvent.NavigateToScreen -> {
                navController.navigate(state.route)
            }
            is NavigationEvent.NavigateUp -> {
                navController.navigateUp()
            }
            is NavigationEvent.NavigateWithDeeplink -> {
                navController.navigate(state.deeplink)
            }
            is NavigationEvent.Empty -> {}
        }

        navigator.consumeEvent()
    }

    NavHost(navController = navController, startDestination = Screen.PokemonListScreen.route) {
        composable(route = Screen.PokemonListScreen.route) {
            val viewModel = hiltViewModel<PokemonListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PokemonListScreen(state) {
                viewModel.onEvent(it)
            }
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
            PokemonDetailScreen(
                navigateToDetails = { navController.navigateToPokemonDetail(it) },
                navigateUp = { navController.navigateUp() },
            )
        }
    }
}

@Composable
private fun RequestNotificationPermission(context: Context) {
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED,
            )
        } else {
            mutableStateOf(true)
        }
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
            },
        )

    LaunchedEffect(key1 = launcher) {
        if (!hasNotificationPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

private fun NavController.navigateToPokemonDetail(pokemonId: String) {
    this.navigate("${Screen.PokemonDetailScreen.route}/$pokemonId")
}
