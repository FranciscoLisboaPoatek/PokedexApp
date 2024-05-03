import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonDetailScreen
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonDetailViewModel

@Composable
fun PokemonDetailScreenNavGraphComposable() {
    val viewModel = hiltViewModel<PokemonDetailViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    PokemonDetailScreen(
        state = state,
        onEvent = {
            viewModel.onEvent(it)
        },
    )
}
