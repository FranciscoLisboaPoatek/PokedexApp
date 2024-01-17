package com.example.pokedexapp.data.local_database

import androidx.paging.PagingSource
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

    @Query("SELECT * FROM Pokemon")
    fun pokemonPagination(): PagingSource<Int,PokemonDaoDto>

    @Query("DELETE FROM Pokemon")
    fun deleteAllPokemon()
}