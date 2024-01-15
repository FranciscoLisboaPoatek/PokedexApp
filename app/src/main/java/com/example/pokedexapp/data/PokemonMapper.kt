package com.example.pokedexapp.data

import com.example.pokedexapp.data.local_database.PokemonDaoDto
import com.example.pokedexapp.data.network.PokemonApiDto
import com.example.pokedexapp.data.network.PokemonListItemApiDto
import com.example.pokedexapp.data.network.StatListItem
import com.example.pokedexapp.data.network.TypeListItem
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.models.PokemonSprite

object PokemonMapper {
    fun PokemonApiDto.toPokemonModel(): PokemonModel {
        val primaryType = types[0].toPokemonType()
        val secondaryType = if (types.size > 1) types[1].toPokemonType() else null
        return PokemonModel(
            id = id.toString(),
            name = name,
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
        return PokemonDaoDto(name = name, url = url)
    }
}

private fun TypeListItem.toPokemonType(): PokemonTypes? {
    return when (type.name) {
        "normal" -> PokemonTypes.NORMAL
        "fighting" -> PokemonTypes.FIGHTING
        "flying" -> PokemonTypes.FLYING
        "poison" -> PokemonTypes.POISON
        "ground" -> PokemonTypes.GROUND
        "rock" -> PokemonTypes.ROCK
        "bug" -> PokemonTypes.BUG
        "ghost" -> PokemonTypes.GHOST
        "steel" -> PokemonTypes.STEEL
        "fire" -> PokemonTypes.FIRE
        "water" -> PokemonTypes.WATER
        "grass" -> PokemonTypes.GRASS
        "electric" -> PokemonTypes.ELECTRIC
        "psychic" -> PokemonTypes.PSYCHIC
        "ice" -> PokemonTypes.ICE
        "dragon" -> PokemonTypes.DRAGON
        "dark" -> PokemonTypes.DARK
        "fairy" -> PokemonTypes.FAIRY
        else -> null
    }
}

private fun List<StatListItem>.toPokemonBaseStatList(): MutableList<PokemonBaseStats> {
    val pokemonBaseStatsList = mutableListOf<PokemonBaseStats>()
    forEach {
        when (it.stat.name) {
            "hp" -> pokemonBaseStatsList.add(PokemonBaseStats.Hp(it.base_stat))
            "attack" -> pokemonBaseStatsList.add(PokemonBaseStats.Attack(it.base_stat))
            "defense" -> pokemonBaseStatsList.add(PokemonBaseStats.Defense(it.base_stat))
            "special-attack" -> pokemonBaseStatsList.add(PokemonBaseStats.SpecialAttack(it.base_stat))
            "special-defense" -> pokemonBaseStatsList.add(PokemonBaseStats.SpecialDefense(it.base_stat))
            "speed" -> pokemonBaseStatsList.add(PokemonBaseStats.Speed(it.base_stat))
        }
    }
    return pokemonBaseStatsList
}

private fun Int.decimeterToMeter(): Float {
    return this.toFloat() / 10
}

private fun Int.hectogramsToKg(): Float {
    return this.toFloat() / 10
}
