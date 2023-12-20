package com.example.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.theme.PokedexAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonList = listOf(
            PokemonModel(
                1,
                "Ditto",
                PokemonTypes.NORMAL,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
            ),
            PokemonModel(
                2,
                "Lucario",
                PokemonTypes.FIGHTING,
                PokemonTypes.STEEL,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png"
            ),
            PokemonModel(
                3,
                "Noivern",
                PokemonTypes.FLYING,
                PokemonTypes.DRAGON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png"
            ),
            PokemonModel(
                4,
                "Crobat",
                PokemonTypes.POISON,
                PokemonTypes.FLYING,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png"
            ),
            PokemonModel(
                5,
                "Krookodile",
                PokemonTypes.GROUND,
                PokemonTypes.DARK,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png"
            ),
            PokemonModel(
                6,
                "Omanyte",
                PokemonTypes.ROCK,
                PokemonTypes.WATER,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png"
            ),
            PokemonModel(
                7,
                "Volcarona",
                PokemonTypes.BUG,
                PokemonTypes.FIRE,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png"
            ),
            PokemonModel(
                8,
                "Gengar",
                PokemonTypes.GHOST,
                PokemonTypes.POISON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png"
            ),
            PokemonModel(
                9,
                "Jirachi",
                PokemonTypes.STEEL,
                PokemonTypes.PSYCHIC,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png"
            ),
            PokemonModel(
                10,
                "Ho-oh",
                PokemonTypes.FIRE,
                PokemonTypes.FLYING,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png"
            ),
            PokemonModel(
                11,
                "Blastoise",
                PokemonTypes.WATER,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"
            ),
            PokemonModel(
                12,
                "Roserade",
                PokemonTypes.GRASS,
                PokemonTypes.POISON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png"
            ),
            PokemonModel(
                13,
                "Rotom",
                PokemonTypes.ELECTRIC,
                PokemonTypes.GHOST,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png"
            ),
            PokemonModel(
                14,
                "Hatterene",
                PokemonTypes.PSYCHIC,
                PokemonTypes.FAIRY,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png"
            ),
            PokemonModel(
                15,
                "Spheal",
                PokemonTypes.ICE,
                PokemonTypes.WATER,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png"
            ),
            PokemonModel(
                16,
                "Dragapult",
                PokemonTypes.DRAGON,
                PokemonTypes.GHOST,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png"
            ),
            PokemonModel(
                17,
                "Darkrai",
                PokemonTypes.DARK,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png"
            ),
            PokemonModel(
                18,
                "Floette",
                PokemonTypes.FAIRY,
                null,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png"
            ),
        )

        setContent {
            PokedexAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(pokemonList) { pokemon ->
                            PokemonListItem(pokemon = pokemon, strokeWidthDp = 10.dp, modifier = Modifier.size(186.dp, 210.dp))
                        }

                    }
                }

            }
        }
    }
}

