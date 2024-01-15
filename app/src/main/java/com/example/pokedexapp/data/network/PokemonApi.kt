package com.example.pokedexapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): PokemonApiDto

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): PokemonApiDto

    @GET("pokemon?limit=100000")
    suspend fun getPokemonEntireList(): PokemonListResponse
}