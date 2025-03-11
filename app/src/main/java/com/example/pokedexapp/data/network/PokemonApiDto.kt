package com.example.pokedexapp.data.network

import com.squareup.moshi.Json

data class PokemonApiDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val species: BasicApiModel,
    val sprites: SpritesDto,
    val stats: List<StatListItem>,
    val types: List<TypeListItem>,
)

data class SpritesDto(
    @Json(name = "back_default")
    val backDefault: String?,
    @Json(name = "back_shiny")
    val backShiny: String?,
    @Json(name = "front_default")
    val frontDefault: String?,
    @Json(name = "front_shiny")
    val frontShiny: String?,
)

data class StatListItem(
    @Json(name = "base_stat")
    val baseStat: Int,
    val stat: Stat,
)

data class Stat(
    val name: String,
)

data class TypeListItem(
    val type: Type,
)

data class Type(
    val name: String,
)

