package com.example.pokedexapp.ui.pokemon_list_screen

import android.content.Context

sealed class PokemonListScreenOnEvent() {
    object OnSearchClick: PokemonListScreenOnEvent()
    data class OnSendNotificationClick(val context: Context): PokemonListScreenOnEvent()
    data class OnSearchTextValueChange(val text:String): PokemonListScreenOnEvent()
    data class OnPokemonCLick(val pokemonId: String): PokemonListScreenOnEvent()
    object AppendToList: PokemonListScreenOnEvent()
}