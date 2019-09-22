package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class StandardStandingsRaw(
    @field:Json(name = "seasonYear") val seasonYear: String,
    @field:Json(name = "conference") val conference: ConfStandingsRaw
)