package com.example.pokedexapp.ui.pokemon_detail_screen

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.models.SpriteType
import com.example.pokedexapp.domain.use_cases.ChoosePokemonWidgetUseCase
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.PokemonEvolutionChainUseCase
import com.example.pokedexapp.domain.use_cases.SharePokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.utils.AudioPlayer
import com.example.pokedexapp.ui.utils.POKEMON_ID_KEY
import com.example.pokedexapp.ui.utils.updateState
import com.example.pokedexapp.ui.widgets.choose_pokemon.ChoosePokemonWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val pokemonDetailUseCase: PokemonDetailUseCase,
        private val sharePokemonUseCase: SharePokemonUseCase,
        private val pokemonEvolutionChainUseCase: PokemonEvolutionChainUseCase,
        private val choosePokemonWidgetUseCase: ChoosePokemonWidgetUseCase,
        private val navigator: Navigator,
    ) : ViewModel() {
        private val pokemonId: String = checkNotNull(savedStateHandle[POKEMON_ID_KEY])
        private val _state = MutableStateFlow(PokemonDetailScreenUiState())
        val state get() = _state

        init {
            updatePokemon(pokemonId = pokemonId)
        }

        fun onEvent(event: PokemonDetailScreenOnEvent) {
            when (event) {
                is PokemonDetailScreenOnEvent.ChangeShinySprite -> {
                    changeShinyPokemonSprite(event.sprite)
                }

                is PokemonDetailScreenOnEvent.NavigateUp -> {
                    navigator.navigateUp()
                }

                is PokemonDetailScreenOnEvent.NavigateToDetails -> {
                    navigator.navigateTo(Screen.PokemonDetailScreen.navigateToPokemonDetail(event.pokemonId))
                }

                is PokemonDetailScreenOnEvent.RotateSprite -> {
                    rotatePokemonSprite(event.sprite)
                }

                is PokemonDetailScreenOnEvent.OnReceiverTokenChange -> {
                    updateReceiverToken(event.text)
                }

                is PokemonDetailScreenOnEvent.SharePokemonToReceiver -> {
                    sharePokemonToReceiver()
                }

                is PokemonDetailScreenOnEvent.SwitchIsSharingPokemonToReceiver -> {
                    switchSharePokemonToReceiverDialog()
                }

                is PokemonDetailScreenOnEvent.PlayPokemonCry -> {
                    _state.value.pokemonDetailModel?.latestCry?.let {
                        AudioPlayer(context = event.context).play(
                            it,
                        )
                    }
                }

                is PokemonDetailScreenOnEvent.SwitchIsSettingPokemonAsWidget -> {
                    switchSetPokemonAsWidgetDialog()
                }

                is PokemonDetailScreenOnEvent.SetChoosePokemonWidgetModel -> {
                    setChoosePokemonWidgetModel(event.pokemon, event.context)
                }
            }
        }

        fun switchSharePokemonToReceiverDialog() {
            _state.updateState { copy(isSharingPokemonToReceiver = !isSharingPokemonToReceiver) }
        }

        fun switchSetPokemonAsWidgetDialog() {
            _state.value.pokemonDetailModel?.let {
                navigator.navigateTo(Screen.ChoosePokemonWidgetScreen.navigateToChoosePokemonWidget(it.id))
            }
        }

        fun setChoosePokemonWidgetModel(
            pokemon: ChoosePokemonWidgetModel,
            context: Context,
        ) {
            viewModelScope.launch {
                choosePokemonWidgetUseCase.savePokemon(pokemon)
                ChoosePokemonWidget().updateAll(context)
            }
        }

        fun updateReceiverToken(newToken: String) {
            _state.updateState { copy(receiverToken = newToken) }
        }

        fun changeShinyPokemonSprite(actualPokemonSprite: SpriteType) {
            val sprite =
                when (actualPokemonSprite) {
                    SpriteType.FRONT_DEFAULT -> state.value.pokemonDetailModel?.frontShinySprite
                    SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonDetailModel?.frontDefaultSprite
                    SpriteType.BACK_DEFAULT -> state.value.pokemonDetailModel?.backShinySprite
                    SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonDetailModel?.backDefaultSprite
                }
            _state.updateState { copy(pokemonSprite = sprite) }
        }

        fun rotatePokemonSprite(actualPokemonSprite: SpriteType) {
            val sprite =
                when (actualPokemonSprite) {
                    SpriteType.FRONT_DEFAULT -> state.value.pokemonDetailModel?.backDefaultSprite
                    SpriteType.FRONT_SHINY_DEFAULT -> state.value.pokemonDetailModel?.backShinySprite
                    SpriteType.BACK_DEFAULT -> state.value.pokemonDetailModel?.frontDefaultSprite
                    SpriteType.BACK_SHINY_DEFAULT -> state.value.pokemonDetailModel?.frontShinySprite
                }
            _state.updateState { copy(pokemonSprite = sprite) }
        }

        fun sharePokemonToReceiver() {
            _state.updateState { copy(isErrorSharingPokemonToReceiver = false) }

            viewModelScope.launch {
                _state.value.pokemonDetailModel?.let {
                    val sharePokemonResponse =
                        sharePokemonUseCase.sharePokemonTo(
                            SharePokemonModel(
                                receiver = _state.value.receiverToken,
                                deeplink = Screen.PokemonDetailScreen.makeDeeplink(it.id),
                                pokemonName = it.name,
                            ),
                        )

                    if (sharePokemonResponse is Response.Error) {
                        _state.updateState { copy(isErrorSharingPokemonToReceiver = true) }
                    }
                }
            }
        }

        fun updatePokemon(pokemonId: String) {
            _state.updateState { copy(isLoading = true) }
            viewModelScope.launch {
                val pokemonDetailModelResponse =
                    pokemonDetailUseCase.getPokemonById(pokemonId = pokemonId)

                when (pokemonDetailModelResponse) {
                    is Response.Error -> {
                        _state.updateState { copy(isError = true, isLoading = false) }
                    }

                    is Response.Success -> {
                        val evolutionChainResponse =
                            pokemonEvolutionChainUseCase.getPokemonChain(speciesId = pokemonDetailModelResponse.data.speciesId)

                        when (evolutionChainResponse) {
                            is Response.Error -> {
                                _state.updateState { copy(isError = true, isLoading = false) }
                            }

                            is Response.Success -> {
                                _state.updateState {
                                    copy(
                                        isLoading = false,
                                        isError = false,
                                        pokemonDetailModel = pokemonDetailModelResponse.data,
                                        pokemonSprite = pokemonDetailModelResponse.data.frontDefaultSprite,
                                        evolutionChain =
                                            if (evolutionChainResponse.data.basePokemon?.evolutions?.isEmpty() == true) {
                                                null
                                            } else {
                                                evolutionChainResponse.data
                                            },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
