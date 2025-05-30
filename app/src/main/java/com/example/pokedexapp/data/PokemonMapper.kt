package com.example.pokedexapp.data

import com.example.pokedexapp.data.local_database.PokemonDaoDto
import com.example.pokedexapp.data.network.BasicApiModelDto
import com.example.pokedexapp.data.network.PokemonApiDto
import com.example.pokedexapp.data.network.PokemonEvolutionChainDto
import com.example.pokedexapp.data.network.StatListItem
import com.example.pokedexapp.data.network.TypeListItem
import com.example.pokedexapp.data.pokedex_server.SharePokemonDto
import com.example.pokedexapp.data.utils.extractPokemonIdFromUrl
import com.example.pokedexapp.data.utils.treatName
import com.example.pokedexapp.domain.models.ChainModel
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.models.PokemonTypes.Companion.getPokemonTypeByString
import com.example.pokedexapp.domain.models.SharePokemonModel

object PokemonMapper {
    fun PokemonApiDto.toPokemonModel(): PokemonDetailModel {
        val pokemonTypes = this.types.toPokemonTypes()
        return PokemonDetailModel(
            id = id.toString(),
            speciesId = species.url.extractPokemonIdFromUrl().toString(),
            name = name.treatName(),
            height = height.decimeterToMeter(),
            weight = weight.hectogramsToKg(),
            baseStats = stats.toPokemonBaseStatList(),
            primaryType = pokemonTypes.first,
            secondaryType = pokemonTypes.second,
            frontDefaultSprite = PokemonSprite.FrontDefaultSprite(sprites.frontDefault),
            frontShinySprite = PokemonSprite.FrontShinySprite(sprites.frontShiny),
            backDefaultSprite = PokemonSprite.BackDefaultSprite(sprites.backDefault),
            backShinySprite = PokemonSprite.BackShinySprite(sprites.backShiny),
            latestCry = cries.latest,
        )
    }

    fun List<TypeListItem>.toPokemonTypes(): Pair<PokemonTypes, PokemonTypes?> {
        val primaryType = this[0].toPokemonType() ?: throw NoSuchElementException()
        val secondaryType = if (this.size > 1) this[1].toPokemonType() else null
        return Pair(primaryType, secondaryType)
    }

    fun PokemonApiDto.toPokemonListItemModel(): PokemonListItemModel {
        val primaryType = types[0].toPokemonType()
        val secondaryType = if (types.size > 1) types[1].toPokemonType() else null
        return PokemonListItemModel(
            id = id.toString(),
            name = name.treatName(),
            spriteUrl = sprites.frontDefault,
            primaryType = primaryType ?: PokemonTypes.NORMAL,
            secondaryType = secondaryType,
        )
    }

    fun BasicApiModelDto.toPokemonDaoDto(): PokemonDaoDto {
        return PokemonDaoDto(id = url.extractPokemonIdFromUrl(), name = name.treatName(), url = url)
    }

    fun PokemonDaoDto.toPokemonMinimalInfo(): PokemonMinimalInfo {
        return PokemonMinimalInfo(id = id.toString(), name = name)
    }

    fun SharePokemonModel.toSharePokemonNotificationDto(): SharePokemonDto {
        return SharePokemonDto(
            receiver = receiver,
            deeplink = deeplink,
            pokemonName = pokemonName,
        )
    }

    fun PokemonEvolutionChainDto.toPokemonEvolutionChainModel(chain: ChainModel): PokemonEvolutionChainModel {
        return PokemonEvolutionChainModel(
            id = this.id.toString(),
            basePokemon = chain,
        )
    }
}

private fun TypeListItem.toPokemonType(): PokemonTypes? {
    return getPokemonTypeByString(this.type.name)
}

private fun List<StatListItem>.toPokemonBaseStatList(): MutableList<PokemonBaseStats> {
    val pokemonBaseStatsList = mutableListOf<PokemonBaseStats>()
    this.forEach {
        PokemonBaseStats.getPokemonBaseStatByString(it.stat.name, it.baseStat)
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
