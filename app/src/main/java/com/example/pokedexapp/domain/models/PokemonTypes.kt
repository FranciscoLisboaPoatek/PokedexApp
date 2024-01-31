package com.example.pokedexapp.domain.models

import androidx.compose.ui.graphics.Color
import com.example.pokedexapp.ui.theme.BugTypeBackgroundColor
import com.example.pokedexapp.ui.theme.BugTypeColor
import com.example.pokedexapp.ui.theme.DarkTypeBackgroundColor
import com.example.pokedexapp.ui.theme.DarkTypeColor
import com.example.pokedexapp.ui.theme.DragonTypeBackgroundColor
import com.example.pokedexapp.ui.theme.DragonTypeColor
import com.example.pokedexapp.ui.theme.ElectricTypeBackgroundColor
import com.example.pokedexapp.ui.theme.ElectricTypeColor
import com.example.pokedexapp.ui.theme.FairyTypeBackgroundColor
import com.example.pokedexapp.ui.theme.FairyTypeColor
import com.example.pokedexapp.ui.theme.FightingTypeBackgroundColor
import com.example.pokedexapp.ui.theme.FightingTypeColor
import com.example.pokedexapp.ui.theme.FireTypeBackgroundColor
import com.example.pokedexapp.ui.theme.FireTypeColor
import com.example.pokedexapp.ui.theme.FlyingTypeBackgroundColor
import com.example.pokedexapp.ui.theme.FlyingTypeColor
import com.example.pokedexapp.ui.theme.GhostTypeBackgroundColor
import com.example.pokedexapp.ui.theme.GhostTypeColor
import com.example.pokedexapp.ui.theme.GrassTypeBackgroundColor
import com.example.pokedexapp.ui.theme.GrassTypeColor
import com.example.pokedexapp.ui.theme.GroundTypeBackgroundColor
import com.example.pokedexapp.ui.theme.GroundTypeColor
import com.example.pokedexapp.ui.theme.IceTypeBackgroundColor
import com.example.pokedexapp.ui.theme.IceTypeColor
import com.example.pokedexapp.ui.theme.NormalTypeBackgroundColor
import com.example.pokedexapp.ui.theme.NormalTypeColor
import com.example.pokedexapp.ui.theme.PoisonTypeBackgroundColor
import com.example.pokedexapp.ui.theme.PoisonTypeColor
import com.example.pokedexapp.ui.theme.PsychicTypeBackgroundColor
import com.example.pokedexapp.ui.theme.PsychicTypeColor
import com.example.pokedexapp.ui.theme.RockTypeBackgroundColor
import com.example.pokedexapp.ui.theme.RockTypeColor
import com.example.pokedexapp.ui.theme.SteelTypeBackgroundColor
import com.example.pokedexapp.ui.theme.SteelTypeColor
import com.example.pokedexapp.ui.theme.WaterTypeBackgroundColor
import com.example.pokedexapp.ui.theme.WaterTypeColor

enum class PokemonTypes(val color: Color, val backgroundColor: Color) {
    NORMAL(NormalTypeColor, NormalTypeBackgroundColor),
    FIGHTING(FightingTypeColor, FightingTypeBackgroundColor),
    FLYING(FlyingTypeColor, FlyingTypeBackgroundColor),
    POISON(PoisonTypeColor, PoisonTypeBackgroundColor),
    GROUND(GroundTypeColor, GroundTypeBackgroundColor),
    ROCK(RockTypeColor, RockTypeBackgroundColor),
    BUG(BugTypeColor, BugTypeBackgroundColor),
    GHOST(GhostTypeColor, GhostTypeBackgroundColor),
    STEEL(SteelTypeColor, SteelTypeBackgroundColor),
    FIRE(FireTypeColor, FireTypeBackgroundColor),
    WATER(WaterTypeColor, WaterTypeBackgroundColor),
    GRASS(GrassTypeColor, GrassTypeBackgroundColor),
    ELECTRIC(ElectricTypeColor, ElectricTypeBackgroundColor),
    PSYCHIC(PsychicTypeColor, PsychicTypeBackgroundColor),
    ICE(IceTypeColor, IceTypeBackgroundColor),
    DRAGON(DragonTypeColor, DragonTypeBackgroundColor),
    DARK(DarkTypeColor, DarkTypeBackgroundColor),
    FAIRY(FairyTypeColor, FairyTypeBackgroundColor);

    companion object {
        fun getPokemonTypeByString(pokemonTypeString: String): PokemonTypes? {
            return when (pokemonTypeString) {
                "normal" -> NORMAL
                "fighting" -> FIGHTING
                "flying" -> FLYING
                "poison" -> POISON
                "ground" -> GROUND
                "rock" -> ROCK
                "bug" -> BUG
                "ghost" -> GHOST
                "steel" -> STEEL
                "fire" -> FIRE
                "water" -> WATER
                "grass" -> GRASS
                "electric" -> ELECTRIC
                "psychic" -> PSYCHIC
                "ice" -> ICE
                "dragon" -> DRAGON
                "dark" -> DARK
                "fairy" -> FAIRY
                else -> null
            }
        }
    }
}