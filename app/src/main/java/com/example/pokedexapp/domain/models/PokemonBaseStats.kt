package com.example.pokedexapp.domain.models

import androidx.compose.ui.graphics.Color
import com.example.pokedexapp.ui.theme.AttackStatColor
import com.example.pokedexapp.ui.theme.DefenseStatColor
import com.example.pokedexapp.ui.theme.HpStatColor
import com.example.pokedexapp.ui.theme.SpAttackStatColor
import com.example.pokedexapp.ui.theme.SpDefenseStatColor
import com.example.pokedexapp.ui.theme.SpeedStatColor

sealed class PokemonBaseStats(val value: Int, val color: Color) {
    class Hp(stat: Int):PokemonBaseStats(value = stat, color = HpStatColor)
    class Attack(stat: Int):PokemonBaseStats(value = stat, color = AttackStatColor)
    class Defense(stat: Int):PokemonBaseStats(value = stat, color = DefenseStatColor)
    class SpecialAttack(stat: Int):PokemonBaseStats(value = stat, color = SpAttackStatColor)
    class SpecialDefense(stat: Int):PokemonBaseStats(value = stat, color = SpDefenseStatColor)
    class Speed(stat: Int):PokemonBaseStats(value = stat, color = SpeedStatColor)

    companion object {
        fun getPokemonBaseStatByString(
            pokemonBaseStatString: String,
            baseStatValue: Int
        ): PokemonBaseStats? {
            return when (pokemonBaseStatString) {
                "hp" -> Hp(baseStatValue)
                "attack" -> Attack(baseStatValue)
                "defense" -> Defense(baseStatValue)
                "special-attack" -> SpecialAttack(baseStatValue)
                "special-defense" -> SpecialDefense(baseStatValue)
                "speed" -> Speed(baseStatValue)
                else -> null
            }
        }
    }
}
