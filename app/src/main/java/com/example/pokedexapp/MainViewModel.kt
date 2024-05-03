package com.example.pokedexapp

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.pokedexapp.ui.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val navigator: Navigator,
    ) : ViewModel() {
        fun handleDeepLink(uri: Uri) {
            navigator.handleDeeplink(uri)
        }
    }
