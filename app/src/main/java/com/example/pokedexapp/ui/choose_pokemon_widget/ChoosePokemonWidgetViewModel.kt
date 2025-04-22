package com.example.pokedexapp.ui.choose_pokemon_widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.use_cases.ChoosePokemonWidgetUseCase
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.utils.POKEMON_ID_KEY
import com.example.pokedexapp.ui.widgets.choose_pokemon.ChoosePokemonWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoosePokemonWidgetViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val pokemonDetailUseCase: PokemonDetailUseCase,
        private val choosePokemonWidgetUseCase: ChoosePokemonWidgetUseCase,
        private val navigator: Navigator,
    ) : ViewModel() {
        private val pokemonId: String = checkNotNull(savedStateHandle[POKEMON_ID_KEY])

        private val _state: MutableStateFlow<ChoosePokemonAsWidgetUiState> = MutableStateFlow(ChoosePokemonAsWidgetUiState())
        val state get() = _state

        init {
            loadData()
        }

        fun onEvent(event: ChoosePokemonAsWidgetScreenOnEvent) {
            when (event) {
                ChoosePokemonAsWidgetScreenOnEvent.OnCancel -> {
                    onCancel()
                }
                is ChoosePokemonAsWidgetScreenOnEvent.OnChoosePokemonWidgetModel -> {
                    onChoosePokemonWidgetModel(event.pokemon, event.context)
                }
            }
        }

        private fun onChoosePokemonWidgetModel(
            pokemon: ChoosePokemonWidgetModel,
            context: Context,
        ) {
            viewModelScope.launch {
                choosePokemonWidgetUseCase.savePokemon(pokemon)
                ChoosePokemonWidget().updateAll(context)
                navigator.navigateUp()
            }
        }

        private fun onCancel() {
            navigator.navigateUp()
        }

        private fun loadData() {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            viewModelScope.launch {
                val pokemonDetailResponse = pokemonDetailUseCase.getPokemonById(pokemonId)

                when (pokemonDetailResponse) {
                    is Response.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }
                    is Response.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                choosePokemonAsWidgetScreenModel =
                                    ChoosePokemonAsWidgetScreenModel(
                                        pokemonId = pokemonDetailResponse.data.id,
                                        pokemonFrontSpriteImageUrl = pokemonDetailResponse.data.frontDefaultSprite,
                                        pokemonFrontShinySpriteImageUrl = pokemonDetailResponse.data.frontShinySprite,
                                        pokemonBackSpriteImageUrl = pokemonDetailResponse.data.backDefaultSprite,
                                        pokemonBackShinySpriteImageUrl = pokemonDetailResponse.data.backShinySprite,
                                        pokemonPrimaryType = pokemonDetailResponse.data.primaryType,
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }

sealed class ChoosePokemonAsWidgetScreenOnEvent {
    data class OnChoosePokemonWidgetModel(
        val pokemon: ChoosePokemonWidgetModel,
        val context: Context,
    ) : ChoosePokemonAsWidgetScreenOnEvent()

    data object OnCancel : ChoosePokemonAsWidgetScreenOnEvent()
}

data class ChoosePokemonAsWidgetUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val choosePokemonAsWidgetScreenModel: ChoosePokemonAsWidgetScreenModel? = null,
)

data class ChoosePokemonAsWidgetScreenModel(
    val pokemonId: String,
    val pokemonFrontSpriteImageUrl: PokemonSprite.FrontDefaultSprite,
    val pokemonFrontShinySpriteImageUrl: PokemonSprite.FrontShinySprite,
    val pokemonBackSpriteImageUrl: PokemonSprite.BackDefaultSprite,
    val pokemonBackShinySpriteImageUrl: PokemonSprite.BackShinySprite,
    val pokemonPrimaryType: PokemonTypes,
)
