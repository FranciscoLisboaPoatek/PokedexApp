package com.example.pokedexapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.ui.components.RequestNotificationPermission
import com.example.pokedexapp.ui.navigation.NavigationEvent
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.navigation.nav_graph.NavHostComposable
import com.example.pokedexapp.ui.theme.PokedexAppTheme
import com.example.pokedexapp.ui.utils.INTENT_EXTRA_DEEPLINK_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
            navigator.handleDeeplink(deeplink.toUri())
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

    NavHostComposable(navController = navController)
}
