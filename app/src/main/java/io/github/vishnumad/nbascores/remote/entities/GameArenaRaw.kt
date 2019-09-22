package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class GameArenaRaw(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "city") val city: String,
    @field:Json(name = "stateAbbr") val state: String,
    @field:Json(name = "country") val country: String
)