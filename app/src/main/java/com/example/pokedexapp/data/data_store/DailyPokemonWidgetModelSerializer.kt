package com.example.pokedexapp.data.data_store

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.dailyPokemonDataStore by dataStore("daily_pokemon.json", DailyPokemonWidgetModelSerializer)

object DailyPokemonWidgetModelSerializer: Serializer<DailyPokemonWidgetModel> {
    override val defaultValue: DailyPokemonWidgetModel
        get() = DailyPokemonWidgetModel(
            PokemonSampleData.pokemonWidgetDataSample().id,
            PokemonSampleData.pokemonWidgetDataSample().imageUrl,
            PokemonSampleData.pokemonWidgetDataSample().primaryType,
        )

    override suspend fun readFrom(input: InputStream): DailyPokemonWidgetModel {
        try {
            return Json.decodeFromString(
                DailyPokemonWidgetModel.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read DailyPokemonWidgetModel", serialization)
        }
    }

    override suspend fun writeTo(t: DailyPokemonWidgetModel, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(DailyPokemonWidgetModel.serializer(), t)
                    .encodeToByteArray()
            )
        }
    }
}