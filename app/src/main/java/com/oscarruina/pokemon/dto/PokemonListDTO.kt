package com.oscarruina.pokemon.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListDTO(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDTO>
)
