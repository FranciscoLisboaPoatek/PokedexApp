package com.example.pokedexapp.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonDaoDto::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}