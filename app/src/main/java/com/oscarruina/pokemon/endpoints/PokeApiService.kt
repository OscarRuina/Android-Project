package com.oscarruina.pokemon.endpoints

import com.oscarruina.pokemon.dto.PokemonDetailsDTO
import com.oscarruina.pokemon.dto.PokemonListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokeApiService {

    @GET("pokemon")
    fun getAllPokemon() : Call<PokemonListDTO>

    @GET("pokemon/{name}")
    fun getPokemonByName(@Path("name") name: String): Call<PokemonDetailsDTO>

    @GET
    fun getPokemonDetailsByUrl(@Url url: String): Call<PokemonDetailsDTO>

    @GET
    fun getPokemonByUrl(@Url url: String): Call<PokemonListDTO>

}