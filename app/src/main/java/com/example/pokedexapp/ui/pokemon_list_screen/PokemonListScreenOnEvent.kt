package com.example.pokedexapp.ui.pokemon_list_screen

sealed class PokemonListScreenOnEvent() {
    object OnSearchClick: PokemonListScreenOnEvent()
    data class OnSearchTextValueChange(val text:String): PokemonListScreenOnEvent()
    data class OnPokemonCLick(val pokemonId: String): PokemonListScreenOnEvent()
}