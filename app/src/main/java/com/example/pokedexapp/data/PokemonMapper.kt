package com.example.pokedexapp.data

import com.example.pokedexapp.data.network.PokemonApiDto
import com.example.pokedexapp.data.network.StatListItem
import com.example.pokedexapp.data.network.TypeListItem
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.models.PokemonTypes.Companion.getPokemonTypeByString
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonSprite

object PokemonMapper {
    fun pokemonApiDtoToPokemonModel(pokemonApiDto: PokemonApiDto): PokemonModel {
        val primaryType = pokemonApiDtoTypeToPokemonTypes(pokemonApiDto.types[0])
        val secondaryType = if (pokemonApiDto.types.size>1) pokemonApiDtoTypeToPokemonTypes(pokemonApiDto.types[1]) else null
        return PokemonModel(
            id = pokemonApiDto.id.toString(),
            name = pokemonApiDto.name,
            height = decimeterToMeter(pokemonApiDto.height),
            weight = hectogramsToKg(pokemonApiDto.weight),
            baseStats = pokemonApiDtoStatListToPokemonBaseStat(pokemonApiDto.stats),
            primaryType = primaryType ?: PokemonTypes.NORMAL,
            secondaryType = secondaryType,
            frontDefaultSprite = PokemonSprite.FrontDefaultSprite(pokemonApiDto.sprites.front_default),
            frontShinySprite = PokemonSprite.FrontShinySprite(pokemonApiDto.sprites.front_shiny),
            backDefaultSprite = PokemonSprite.BackDefaultSprite(pokemonApiDto.sprites.back_default),
            backShinySprite = PokemonSprite.BackShinySprite(pokemonApiDto.sprites.back_shiny)
        )
    }
}

private fun pokemonApiDtoTypeToPokemonTypes(typeListItem: TypeListItem): PokemonTypes?{
    return getPokemonTypeByString(typeListItem.type.name)
}
private fun pokemonApiDtoStatListToPokemonBaseStat(stats: List<StatListItem>): MutableList<PokemonBaseStats> {
    val pokemonBaseStatsList = mutableListOf<PokemonBaseStats>()
    stats.forEach{
        PokemonBaseStats.getPokemonBaseStatByString(it.stat.name, it.base_stat)
            ?.let { pokemonBaseState -> pokemonBaseStatsList.add(pokemonBaseState) }
    }
    return pokemonBaseStatsList
}
private fun decimeterToMeter(height: Int): Float {
    return height.toFloat() / 10
}

private fun hectogramsToKg(weight: Int): Float {
    return weight.toFloat() / 10
}
