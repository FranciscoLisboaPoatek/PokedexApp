package com.example.pokedexapp.ui.notifications

import android.content.Context
import java.util.Calendar

interface EnqueueWorker {
    fun enqueue(
        context: Context,
        calendar: Calendar = Calendar.getInstance(),
    )
}
