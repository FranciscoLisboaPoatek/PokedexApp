package com.example.pokedexapp

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val uiState: MutableStateFlow<MainActivityUiState> = MutableStateFlow(MainActivityUiState())
    fun handleDeepLink(uri: Uri){
        uiState.updateState { copy(event = Event.NavigateWithDeeplink(uri)) }
    }

    fun consumeEvent(){
        uiState.updateState { copy(event = null) }
    }
}

sealed class Event(){
    data class NavigateWithDeeplink(val deeplink: Uri): Event()
}

data class MainActivityUiState(
    val event: Event? = null
)