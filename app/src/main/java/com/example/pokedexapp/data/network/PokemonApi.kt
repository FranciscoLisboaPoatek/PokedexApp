package com.example.pokedexapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int,
    ): PokemonApiDto?

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String,
    ): PokemonApiDto?

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpeciesById(
        @Path("id") id: Int,
    ): PokemonSpeciesApiDto

    @GET("evolution-chain/{id}")
    suspend fun getPokemonEvolutionChain(
        @Path("id") id: Int
    ): PokemonEvolutionChainDto

    @GET("pokemon?limit=100000")
    suspend fun getPokemonEntireList(): PokemonListResponse
}
