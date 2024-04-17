package com.example.pokedexapp.data.pokedex_server

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PokedexServerApi {
    @POST("share_pokemon")
    fun sharePokemon(
        @Body body: SharePokemonDto,
    ): Call<Void>
}
