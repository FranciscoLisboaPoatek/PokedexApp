package com.example.pokedexapp.ui.utils

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri

class AudioPlayer(
    private val context: Context,
) {
    fun play(url: String) {
        MediaPlayer.create(context, url.toUri()).start()
    }
}
