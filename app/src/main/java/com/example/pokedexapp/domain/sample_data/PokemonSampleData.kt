package com.example.pokedexapp.domain.sample_data

import com.example.pokedexapp.domain.models.ChainModel
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.models.PokemonTypes

class PokemonSampleData {
    companion object {
        fun pokemonListSampleData(): List<PokemonListItemModel> =
            listOf(
                PokemonListItemModel(
                    id = "132",
                    name = "Ditto",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
                    primaryType = PokemonTypes.NORMAL,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "448",
                    name = "Lucario",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png",
                    primaryType = PokemonTypes.FIGHTING,
                    secondaryType = PokemonTypes.STEEL,
                ),
                PokemonListItemModel(
                    id = "715",
                    name = "Noivern",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png",
                    primaryType = PokemonTypes.FLYING,
                    secondaryType = PokemonTypes.DRAGON,
                ),
                PokemonListItemModel(
                    id = "169",
                    name = "Crobat",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png",
                    primaryType = PokemonTypes.POISON,
                    secondaryType = PokemonTypes.FLYING,
                ),
                PokemonListItemModel(
                    id = "553",
                    name = "Krookodile",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png",
                    primaryType = PokemonTypes.GROUND,
                    secondaryType = PokemonTypes.DARK,
                ),
                PokemonListItemModel(
                    id = "138",
                    name = "Omanyte",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png",
                    primaryType = PokemonTypes.ROCK,
                    secondaryType = PokemonTypes.WATER,
                ),
                PokemonListItemModel(
                    id = "637",
                    name = "Volcarona",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png",
                    primaryType = PokemonTypes.BUG,
                    secondaryType = PokemonTypes.FIRE,
                ),
                PokemonListItemModel(
                    id = "94",
                    name = "Gengar",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png",
                    primaryType = PokemonTypes.GHOST,
                    secondaryType = PokemonTypes.POISON,
                ),
                PokemonListItemModel(
                    id = "385",
                    name = "Jirachi",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png",
                    primaryType = PokemonTypes.STEEL,
                    secondaryType = PokemonTypes.PSYCHIC,
                ),
                PokemonListItemModel(
                    id = "250",
                    name = "Ho-oh",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png",
                    primaryType = PokemonTypes.FIRE,
                    secondaryType = PokemonTypes.FLYING,
                ),
                PokemonListItemModel(
                    id = "9",
                    name = "Blastoise",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
                    primaryType = PokemonTypes.WATER,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "407",
                    name = "Roserade",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png",
                    primaryType = PokemonTypes.GRASS,
                    secondaryType = PokemonTypes.POISON,
                ),
                PokemonListItemModel(
                    id = "479",
                    name = "Rotom",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png",
                    primaryType = PokemonTypes.ELECTRIC,
                    secondaryType = PokemonTypes.GHOST,
                ),
                PokemonListItemModel(
                    id = "858",
                    name = "Hatterene",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png",
                    primaryType = PokemonTypes.PSYCHIC,
                    secondaryType = PokemonTypes.FAIRY,
                ),
                PokemonListItemModel(
                    id = "363",
                    name = "Spheal",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png",
                    primaryType = PokemonTypes.ICE,
                    secondaryType = PokemonTypes.WATER,
                ),
                PokemonListItemModel(
                    id = "887",
                    name = "Dragapult",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png",
                    primaryType = PokemonTypes.DRAGON,
                    secondaryType = PokemonTypes.GHOST,
                ),
                PokemonListItemModel(
                    id = "491",
                    name = "Darkrai",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png",
                    primaryType = PokemonTypes.DARK,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "670",
                    name = "Floette",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png",
                    primaryType = PokemonTypes.FAIRY,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "258",
                    name = "Mudkip",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/258.png",
                    primaryType = PokemonTypes.WATER,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "144",
                    name = "Articuno",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/144.png",
                    primaryType = PokemonTypes.ICE,
                    secondaryType = PokemonTypes.FLYING,
                ),
                PokemonListItemModel(
                    id = "155",
                    name = "Cyndaquil",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/155.png",
                    primaryType = PokemonTypes.FIRE,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "1",
                    name = "Bulbasaur",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    primaryType = PokemonTypes.GRASS,
                    secondaryType = PokemonTypes.POISON,
                ),
                PokemonListItemModel(
                    id = "2",
                    name = "Ivysaur",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                    primaryType = PokemonTypes.GRASS,
                    secondaryType = PokemonTypes.POISON,
                ),
                PokemonListItemModel(
                    id = "3",
                    name = "Venusaur",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                    primaryType = PokemonTypes.GRASS,
                    secondaryType = PokemonTypes.POISON,
                ),
                PokemonListItemModel(
                    id = "4",
                    name = "Charmander",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    primaryType = PokemonTypes.FIRE,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "5",
                    name = "Charmeleon",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
                    primaryType = PokemonTypes.FIRE,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "6",
                    name = "Charizard",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                    primaryType = PokemonTypes.FIRE,
                    secondaryType = PokemonTypes.FLYING,
                ),
                PokemonListItemModel(
                    id = "177",
                    name = "Natu",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/177.png",
                    primaryType = PokemonTypes.PSYCHIC,
                    secondaryType = PokemonTypes.FLYING,
                ),
            )

        fun pokemonDetailListSampleData(): List<PokemonDetailModel> =
            pokemonListSampleData().map {
                PokemonDetailModel(
                    id = it.id,
                    speciesId = "0",
                    name = it.name,
                    height = 0f,
                    weight = 0f,
                    baseStats =
                        listOf(
                            PokemonBaseStats.Hp(0),
                            PokemonBaseStats.Attack(0),
                            PokemonBaseStats.Defense(0),
                            PokemonBaseStats.SpecialAttack(0),
                            PokemonBaseStats.SpecialDefense(0),
                            PokemonBaseStats.Speed(0),
                        ),
                    primaryType = it.primaryType,
                    secondaryType = it.secondaryType,
                    frontDefaultSprite = PokemonSprite.FrontDefaultSprite(it.spriteUrl),
                    frontShinySprite = PokemonSprite.FrontShinySprite(""),
                    backDefaultSprite = PokemonSprite.BackDefaultSprite(""),
                    backShinySprite = PokemonSprite.BackShinySprite(""),
                    latestCry = null,
                )
            }

        fun pokemonSearchListSampleData(): List<PokemonListItemModel> =
            listOf(
                PokemonListItemModel(
                    id = "365",
                    name = "???",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png",
                    primaryType = PokemonTypes.NORMAL,
                    secondaryType = null,
                ),
                PokemonListItemModel(
                    id = "578",
                    name = "???",
                    spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png",
                    primaryType = PokemonTypes.NORMAL,
                    secondaryType = null,
                ),
            )

        fun singlePokemonListItemSampleData(): PokemonListItemModel =
            PokemonListItemModel(
                id = "1",
                name = "Bulbasaur",
                spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                primaryType = PokemonTypes.GRASS,
                secondaryType = PokemonTypes.POISON,
            )

        fun singlePokemonDetailSampleData(): PokemonDetailModel =
            PokemonDetailModel(
                id = "1",
                speciesId = "1",
                name = "Bulbasaur",
                height = 0.7f,
                weight = 6.9f,
                baseStats =
                    listOf(
                        PokemonBaseStats.Hp(45),
                        PokemonBaseStats.Attack(49),
                        PokemonBaseStats.Defense(49),
                        PokemonBaseStats.SpecialAttack(65),
                        PokemonBaseStats.SpecialDefense(65),
                        PokemonBaseStats.Speed(45),
                    ),
                primaryType = PokemonTypes.GRASS,
                secondaryType = PokemonTypes.POISON,
                frontDefaultSprite =
                    PokemonSprite.FrontDefaultSprite(
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    ),
                frontShinySprite =
                    PokemonSprite.FrontShinySprite(
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png",
                    ),
                backDefaultSprite =
                    PokemonSprite.BackDefaultSprite(
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
                    ),
                backShinySprite =
                    PokemonSprite.BackShinySprite(
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/1.png",
                    ),
                latestCry = null,
            )

        fun evolutionChainSampleData(): PokemonEvolutionChainModel =
            PokemonEvolutionChainModel(
                id = "1",
                basePokemon =
                    ChainModel(
                        id = "1",
                        name = "Bulbasaur",
                        isBaby = false,
                        spriteUrl = "",
                        evolutions =
                            listOf(
                                ChainModel(
                                    id = "2",
                                    name = "Ivysaur",
                                    isBaby = false,
                                    spriteUrl = "",
                                    evolutions =
                                        listOf(
                                            ChainModel(
                                                id = "3",
                                                name = "Venusaur",
                                                isBaby = false,
                                                spriteUrl = "",
                                                evolutions = listOf(),
                                            ),
                                        ),
                                ),
                            ),
                    ),
            )
    }
}
