package com.example.pokedexapp

import android.content.Context
import com.example.pokedexapp.ui.notifications.EnqueueWorker
import java.util.Calendar
import javax.inject.Inject

class FakeEnqueueWorker
    @Inject
    constructor() : EnqueueWorker {
        override fun enqueue(
            context: Context,
            calendar: Calendar,
        ) {
        }
    }
