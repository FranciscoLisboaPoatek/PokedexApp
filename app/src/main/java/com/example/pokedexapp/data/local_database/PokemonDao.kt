package com.example.pokedexapp.data.local_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokemon(pokemonList: List<PokemonDaoDto>)

    @Query("SELECT * FROM Pokemon WHERE name LIKE :pokemonName LIMIT :limit OFFSET :offset")
    fun searchPokemonByName(pokemonName: String, offset: Int, limit: Int): List<PokemonDaoDto>

    @Query("SELECT * FROM Pokemon LIMIT :limit OFFSET :offset")
    fun pokemonPagination(offset: Int, limit: Int): List<PokemonDaoDto>

    @Query("DELETE FROM Pokemon")
    fun deleteAllPokemon()

    @Query("SELECT COUNT() FROM Pokemon")
    fun getPokemonTableCount(): Int

    @Query("SELECT * FROM Pokemon LIMIT 1 OFFSET :offset")
    fun getRandomPokemon(offset: Int): PokemonDaoDto
}