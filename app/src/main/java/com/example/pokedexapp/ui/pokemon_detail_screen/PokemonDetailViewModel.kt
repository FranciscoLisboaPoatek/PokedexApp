package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.SpriteType
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.SharePokemonUseCase
import com.example.pokedexapp.ui.Screen
import com.example.pokedexapp.domain.use_cases.PokemonEvolutionChainUseCase
import com.example.pokedexapp.ui.utils.POKEMON_ID_KEY
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonDetailUseCase: PokemonDetailUseCase,
    private val sharePokemonUseCase: SharePokemonUseCase,
    private val pokemonEvolutionChainUseCase: PokemonEvolutionChainUseCase
) : ViewModel() {

    private val pokemonId: String = checkNotNull(savedStateHandle[POKEMON_ID_KEY])
    private val _state = MutableStateFlow(PokemonDetailScreenUiState())
    val state get() = _state

    init {
        updatePokemon(pokemonId = pokemonId)
    }

    fun switchSharePokemonToReceiverDialog(){
        _state.updateState { copy(isSharingPokemonToReceiver = !isSharingPokemonToReceiver) }
    }

    fun updateReceiverToken(newToken: String){
        _state.updateState { copy(receiverToken = newToken) }
    }

    fun changeShinyPokemonSprite(actualPokemonSprite: SpriteType) {
        val sprite = when (actualPokemonSprite) {
            SpriteType.FRONT_DEFAULT -> state.value.pokemonDetailModel?.frontShinySprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonDetailModel?.frontDefaultSprite
            SpriteType.BACK_DEFAULT -> state.value.pokemonDetailModel?.backShinySprite
            SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonDetailModel?.backDefaultSprite
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }

    fun rotatePokemonSprite(actualPokemonSprite: SpriteType) {
        val sprite = when (actualPokemonSprite) {
            SpriteType.FRONT_DEFAULT -> state.value.pokemonDetailModel?.backDefaultSprite
            SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonDetailModel?.backShinySprite
            SpriteType.BACK_DEFAULT -> state.value.pokemonDetailModel?.frontDefaultSprite
            SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonDetailModel?.frontShinySprite
        }
        _state.updateState { copy(pokemonSprite = sprite) }
    }

    fun sharePokemonToReceiver() {
        _state.updateState { copy(isSharingPokemonToReceiver = true) }

        viewModelScope.launch {
            try {
                _state.value.pokemonDetailModel?.let {
                    sharePokemonUseCase.sharePokemonTo(
                        SharePokemonModel(
                            receiver = _state.value.receiverToken,
                            deeplink = Screen.PokemonDetailScreen.makeDeeplink(it.id),
                            pokemonName = it.name
                        )
                    )
                }
            }catch (ex: Exception){
                //To implement error state
            }
        }
    }

    fun updatePokemon(pokemonId: String) {
        _state.updateState { copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val responsePokemonDetailModel = pokemonDetailUseCase.getPokemonById(pokemonId = pokemonId)
                val responseEvolutionChain = responsePokemonDetailModel?.let { getEvolutionChain(speciesId = it.speciesId) }
                _state.updateState {
                    copy(
                        isLoading = false,
                        isError = false,
                        pokemonDetailModel = responsePokemonDetailModel,
                        pokemonSprite = responsePokemonDetailModel?.frontDefaultSprite,
                        evolutionChain = responseEvolutionChain ?: PokemonEvolutionChainModel()
                    )
                }
            } catch (ex: Exception) {
                _state.updateState { copy(isError = true, isLoading = false) }
            }

        }
    }

    private suspend fun getEvolutionChain(speciesId: String): PokemonEvolutionChainModel {
       return pokemonEvolutionChainUseCase.getPokemonChain(speciesId = speciesId)
    }
}