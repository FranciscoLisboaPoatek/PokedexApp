package com.example.pokedexapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id:Int
    ):PokemonApiDto
}