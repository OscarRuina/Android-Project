package com.oscarruina.pokemon.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDTO(
    var name: String,
    var url: String
)
