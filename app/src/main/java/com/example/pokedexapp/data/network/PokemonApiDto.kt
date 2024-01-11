package com.example.pokedexapp.data.network

data class PokemonApiDto(
    val id:Int,
    val name:String,
    val height:Int,
    val weight:Int,
    val sprites:SpritesDto,
    val stats: List<StatListItem>,
    val types: List<TypeListItem>
)
data class SpritesDto(
    val back_default: String,
    val back_shiny: String,
    val front_default: String,
    val front_shiny: String,
)

data class StatListItem(
    val base_stat: Int,
    val stat: Stat
)
data class Stat(
    val name: String
)

data class TypeListItem(
    val type: Type
)
data class Type(
    val name: String
)