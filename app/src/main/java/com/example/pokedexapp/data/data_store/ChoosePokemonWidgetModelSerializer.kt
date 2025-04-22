package com.example.pokedexapp.data.data_store

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.choosePokemonDataStore by dataStore("choose_pokemon.json", ChoosePokemonWidgetModelSerializer)

object ChoosePokemonWidgetModelSerializer : Serializer<ChoosePokemonWidgetModel> {
    override val defaultValue: ChoosePokemonWidgetModel
        get() = PokemonSampleData.choosePokemonWidgetDataSample()

    override suspend fun readFrom(input: InputStream): ChoosePokemonWidgetModel {
        try {
            return Json.decodeFromString(
                ChoosePokemonWidgetModel.serializer(),
                input.readBytes().decodeToString(),
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read ChoosePokemonWidgetModel", serialization)
        }
    }

    override suspend fun writeTo(
        t: ChoosePokemonWidgetModel,
        output: OutputStream,
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(ChoosePokemonWidgetModel.serializer(), t)
                    .encodeToByteArray(),
            )
        }
    }
}
