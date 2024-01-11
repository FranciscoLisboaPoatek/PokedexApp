package com.example.pokedexapp.data

import com.example.pokedexapp.data.network.PokemonApiDto
import com.example.pokedexapp.data.network.StatListItem
import com.example.pokedexapp.data.network.TypeListItem
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
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
    return when(typeListItem.type.name){
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
        else-> null
    }
}
private fun pokemonApiDtoStatListToPokemonBaseStat(stats: List<StatListItem>): MutableList<PokemonBaseStats> {
    val pokemonBaseStatsList = mutableListOf<PokemonBaseStats>()
    stats.forEach{
        when(it.stat.name){
            "hp" -> pokemonBaseStatsList.add(PokemonBaseStats.Hp(it.base_stat))
            "attack" ->pokemonBaseStatsList.add(PokemonBaseStats.Attack(it.base_stat))
            "defense" ->pokemonBaseStatsList.add(PokemonBaseStats.Defense(it.base_stat))
            "special-attack" ->pokemonBaseStatsList.add(PokemonBaseStats.SpecialAttack(it.base_stat))
            "special-defense" ->pokemonBaseStatsList.add(PokemonBaseStats.SpecialDefense(it.base_stat))
            "speed" ->pokemonBaseStatsList.add(PokemonBaseStats.Speed(it.base_stat))
        }
    }
    return pokemonBaseStatsList
}
private fun decimeterToMeter(height: Int): Float {
    return height.toFloat() / 10
}

private fun hectogramsToKg(weight: Int): Float {
    return weight.toFloat() / 10
}
