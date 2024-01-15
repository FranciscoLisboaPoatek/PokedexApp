package com.example.pokedexapp.domain.sample_data

import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.models.PokemonSprite

class PokemonSampleData {

    companion object {
        fun pokemonListSampleData(): List<PokemonModel> = listOf(
            PokemonModel(
                "1",
                "Ditto",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.NORMAL,
                null,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/132.png")
            ),
            PokemonModel(
                "2",
                "Lucario",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.FIGHTING,
                PokemonTypes.STEEL,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/448.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/448.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/448.png")
            ),
            PokemonModel(
                "3",
                "Noivern",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.FLYING,
                PokemonTypes.DRAGON,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/715.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/715.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/715.png")
            ),
            PokemonModel(
                "4",
                "Crobat",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.POISON,
                PokemonTypes.FLYING,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/169.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/169.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/169.png")
            ),
            PokemonModel(
                "5",
                "Krookodile",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.GROUND,
                PokemonTypes.DARK,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/553.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/553.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/553.png")
            ),
            PokemonModel(
                "6",
                "Omanyte",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.ROCK,
                PokemonTypes.WATER,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/138.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/138.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/138.png")
            ),
            PokemonModel(
                "7",
                "Volcarona",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.BUG,
                PokemonTypes.FIRE,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/637.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/637.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/637.png")
            ),
            PokemonModel(
                "8",
                "Gengar",
                1.5f,
                40.5f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(60),
                    PokemonBaseStats.Attack(65),
                    PokemonBaseStats.Defense(60),
                    PokemonBaseStats.SpecialAttack(130),
                    PokemonBaseStats.SpecialDefense(75),
                    PokemonBaseStats.Speed(110)
                ),

                PokemonTypes.GHOST,
                PokemonTypes.POISON,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/94.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/94.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/94.png")
            ),
            PokemonModel(
                "9",
                "Jirachi",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.STEEL,
                PokemonTypes.PSYCHIC,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/385.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/385.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/385.png")
            ),
            PokemonModel(
                "10",
                "Ho-oh",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.FIRE,
                PokemonTypes.FLYING,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/250.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/250.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/250.png")
            ),
            PokemonModel(
                "11",
                "Blastoise",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.WATER,
                null,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/9.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/9.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/9.png")
            ),
            PokemonModel(
                "12",
                "Roserade",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.GRASS,
                PokemonTypes.POISON,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/407.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/407.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/407.png")
            ),
            PokemonModel(
                "13",
                "Rotom",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.ELECTRIC,
                PokemonTypes.GHOST,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/479.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/479.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/479.png")
            ),
            PokemonModel(
                "14",
                "Hatterene",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.PSYCHIC,
                PokemonTypes.FAIRY,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/858.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/858.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/858.png")
            ),
            PokemonModel(
                "15",
                "Spheal",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.ICE,
                PokemonTypes.WATER,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/363.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/363.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/363.png")
            ),
            PokemonModel(
                "16",
                "Dragapult",
                3.0f,
                50.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(88),
                    PokemonBaseStats.Attack(120),
                    PokemonBaseStats.Defense(75),
                    PokemonBaseStats.SpecialAttack(100),
                    PokemonBaseStats.SpecialDefense(75),
                    PokemonBaseStats.Speed(142)
                ),

                PokemonTypes.DRAGON,
                PokemonTypes.GHOST,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/887.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/887.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/887.png")
            ),
            PokemonModel(
                "17",
                "Darkrai",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.DARK,
                null,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/491.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/491.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/491.png")
            ),
            PokemonModel(
                "18",
                "Floette",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.FAIRY,
                null,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/670.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/670.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/670.png")
            ),
        )


        fun pokemonSearchListSampleData(): List<PokemonModel> = listOf(
            PokemonModel(
                "1",
                "???",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),
                PokemonTypes.NORMAL,
                null,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/132.png")
            ),
            PokemonModel(
                "1",
                "???",
                10.0f,
                10.0f,
                baseStats = listOf(
                    PokemonBaseStats.Hp(45),
                    PokemonBaseStats.Attack(49),
                    PokemonBaseStats.Defense(49),
                    PokemonBaseStats.SpecialAttack(65),
                    PokemonBaseStats.SpecialDefense(65),
                    PokemonBaseStats.Speed(45)
                ),

                PokemonTypes.NORMAL,
                null,
                PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png"),
                PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png"),
                PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png"),
                PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/132.png")
            ),
        )

        fun singlePokemonSampleData(): PokemonModel = PokemonModel(
            "1",
            "Bulbasaur",
            0.7f,
            6.9f,
            baseStats = listOf(
                PokemonBaseStats.Hp(45),
                PokemonBaseStats.Attack(49),
                PokemonBaseStats.Defense(49),
                PokemonBaseStats.SpecialAttack(65),
                PokemonBaseStats.SpecialDefense(65),
                PokemonBaseStats.Speed(45)
            ),
            PokemonTypes.GRASS,
            PokemonTypes.POISON,
            PokemonSprite.FrontDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
            PokemonSprite.FrontShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png"),
            PokemonSprite.BackDefaultSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"),
            PokemonSprite.BackShinySprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/1.png")
        )
    }
}