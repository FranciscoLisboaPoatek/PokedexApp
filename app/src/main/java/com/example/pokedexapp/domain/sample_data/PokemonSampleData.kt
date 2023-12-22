package com.example.pokedexapp.domain.sample_data

import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes

class PokemonSampleData {

    companion object {
        fun pokemonListSampleData(): List<PokemonModel> = listOf(
            PokemonModel(
                1,
                "Ditto",
                PokemonTypes.NORMAL,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png"
            ),
            PokemonModel(
                2,
                "Lucario",
                PokemonTypes.FIGHTING,
                PokemonTypes.STEEL,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/448.png"
            ),
            PokemonModel(
                3,
                "Noivern",
                PokemonTypes.FLYING,
                PokemonTypes.DRAGON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/715.png"
            ),
            PokemonModel(
                4,
                "Crobat",
                PokemonTypes.POISON,
                PokemonTypes.FLYING,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/169.png"
            ),
            PokemonModel(
                5,
                "Krookodile",
                PokemonTypes.GROUND,
                PokemonTypes.DARK,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/553.png"
            ),
            PokemonModel(
                6,
                "Omanyte",
                PokemonTypes.ROCK,
                PokemonTypes.WATER,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/138.png"
            ),
            PokemonModel(
                7,
                "Volcarona",
                PokemonTypes.BUG,
                PokemonTypes.FIRE,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/637.png"
            ),
            PokemonModel(
                8,
                "Gengar",
                PokemonTypes.GHOST,
                PokemonTypes.POISON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/94.png"
            ),
            PokemonModel(
                9,
                "Jirachi",
                PokemonTypes.STEEL,
                PokemonTypes.PSYCHIC,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/385.png"
            ),
            PokemonModel(
                10,
                "Ho-oh",
                PokemonTypes.FIRE,
                PokemonTypes.FLYING,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/250.png"
            ),
            PokemonModel(
                11,
                "Blastoise",
                PokemonTypes.WATER,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/9.png"
            ),
            PokemonModel(
                12,
                "Roserade",
                PokemonTypes.GRASS,
                PokemonTypes.POISON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/407.png"
            ),
            PokemonModel(
                13,
                "Rotom",
                PokemonTypes.ELECTRIC,
                PokemonTypes.GHOST,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/479.png"
            ),
            PokemonModel(
                14,
                "Hatterene",
                PokemonTypes.PSYCHIC,
                PokemonTypes.FAIRY,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/858.png"
            ),
            PokemonModel(
                15,
                "Spheal",
                PokemonTypes.ICE,
                PokemonTypes.WATER,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/363.png"
            ),
            PokemonModel(
                16,
                "Dragapult",
                PokemonTypes.DRAGON,
                PokemonTypes.GHOST,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/887.png"
            ),
            PokemonModel(
                17,
                "Darkrai",
                PokemonTypes.DARK,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/491.png"
            ),
            PokemonModel(
                18,
                "Floette",
                PokemonTypes.FAIRY,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/670.png"
            ),
        )

        fun pokemonSearchListSampleData(): List<PokemonModel> = listOf(
            PokemonModel(
                1,
                "???",
                PokemonTypes.NORMAL,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/365.png"
            ),
            PokemonModel(
                1,
                "???",
                PokemonTypes.NORMAL,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/578.png"
            ),
        )

        fun singlePokemonSampleData(): PokemonModel = PokemonModel(
            id = 1,
            name = "Bulbasaur",
            primaryType = PokemonTypes.GRASS,
            secondaryType = PokemonTypes.POISON,
            frontDefaultImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            frontShinyImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png"
        )
    }
}