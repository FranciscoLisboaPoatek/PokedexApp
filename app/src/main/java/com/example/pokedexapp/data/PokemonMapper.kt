package com.example.pokedexapp.data

import com.example.pokedexapp.data.local_database.PokemonDaoDto
import com.example.pokedexapp.data.network.PokemonApiDto
import com.example.pokedexapp.data.network.PokemonListItemApiDto
import com.example.pokedexapp.data.network.StatListItem
import com.example.pokedexapp.data.network.TypeListItem
import com.example.pokedexapp.data.pokedex_server.SharePokemonDto
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.models.PokemonTypes.Companion.getPokemonTypeByString
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.ui.utils.extractPokemonIdFromUrl
import com.example.pokedexapp.ui.utils.treatName

object PokemonMapper {
    fun PokemonApiDto.toPokemonModel(): PokemonModel {
        val primaryType = types[0].toPokemonType()
        val secondaryType = if (types.size > 1) types[1].toPokemonType() else null
        return PokemonModel(
            id = id.toString(),
            name = name.treatName(),
            height = height.decimeterToMeter(),
            weight = weight.hectogramsToKg(),
            baseStats = stats.toPokemonBaseStatList(),
            primaryType = primaryType ?: PokemonTypes.NORMAL,
            secondaryType = secondaryType,
            frontDefaultSprite = PokemonSprite.FrontDefaultSprite(sprites.front_default),
            frontShinySprite = PokemonSprite.FrontShinySprite(sprites.front_shiny),
            backDefaultSprite = PokemonSprite.BackDefaultSprite(sprites.back_default),
            backShinySprite = PokemonSprite.BackShinySprite(sprites.back_shiny)
        )
    }

    fun PokemonListItemApiDto.toPokemonDaoDto(): PokemonDaoDto {
        return PokemonDaoDto(id = url.extractPokemonIdFromUrl(), name = name.treatName(), url = url)
    }

    fun PokemonDaoDto.toPokemonMinimalInfo(): PokemonMinimalInfo {
        return PokemonMinimalInfo(id = id.toString(), name = name)
    }

    fun SharePokemonModel.toSharePokemonNotificationDto(): SharePokemonDto {
        return SharePokemonDto(
            receiver = receiver,
            pokemonId = pokemonId,
            pokemonName = pokemonName
        )
    }
}

private fun TypeListItem.toPokemonType(): PokemonTypes? {
    return getPokemonTypeByString(this.type.name)
}

private fun List<StatListItem>.toPokemonBaseStatList(): MutableList<PokemonBaseStats> {
    val pokemonBaseStatsList = mutableListOf<PokemonBaseStats>()
    this.forEach {
        PokemonBaseStats.getPokemonBaseStatByString(it.stat.name, it.base_stat)
            ?.let { pokemonBaseState -> pokemonBaseStatsList.add(pokemonBaseState) }
    }
    return pokemonBaseStatsList
}

private fun Int.decimeterToMeter(): Float {
    return this.toFloat() / 10
}

private fun Int.hectogramsToKg(): Float {
    return this.toFloat() / 10
}
