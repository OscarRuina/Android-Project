package com.oscarruina.pokemon.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailsDTO(
    var id: Int,
    var name: String,
    var height: Int,
    var weight: Int,
    var sprites: Sprites,
    var stats: List<Stat>,
    var types: List<Type>

)

data class Sprites(
    var front_default: String,
    var other: Other
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String,
    val url: String
)

data class Type(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String,
    val url: String
)

data class Other(
    var dream_world: DreamWorld,
    var home: Home
)

data class DreamWorld (
    var front_default: String
)

data class Home(
    var front_default: String
)



