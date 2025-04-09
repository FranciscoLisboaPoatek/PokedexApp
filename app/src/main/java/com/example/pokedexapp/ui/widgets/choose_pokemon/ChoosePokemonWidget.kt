package com.example.pokedexapp.ui.widgets.choose_pokemon

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.example.pokedexapp.domain.use_cases.ChoosePokemonWidgetUseCase
import com.example.pokedexapp.ui.utils.getImageBitmapFromUrl
import com.example.pokedexapp.ui.widgets.PokemonWidgetContent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

class ChoosePokemonWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val choosePokemonUseCase: ChoosePokemonWidgetUseCase = ChoosePokemonWidgetUseCaseEntrypoint.get(context)

        val choosePokemonState = choosePokemonUseCase.getPokemon()

        provideContent {
            var imageBitmap by remember {
                mutableStateOf<Bitmap?>(null)
            }

            LaunchedEffect(choosePokemonState.imageUrl) {
                imageBitmap = getImageBitmapFromUrl(context, choosePokemonState.imageUrl)
            }

            PokemonWidgetContent(
                GlanceModifier,
                choosePokemonState.id,
                imageBitmap,
                choosePokemonState.primaryType,
            )
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface ChoosePokemonWidgetUseCaseEntrypoint {
    fun getChoosePokemonWidgetUseCase(): ChoosePokemonWidgetUseCase

    companion object {
        fun get(context: Context): ChoosePokemonWidgetUseCase =
            EntryPoints.get(context, ChoosePokemonWidgetUseCaseEntrypoint::class.java).getChoosePokemonWidgetUseCase()
    }
}
