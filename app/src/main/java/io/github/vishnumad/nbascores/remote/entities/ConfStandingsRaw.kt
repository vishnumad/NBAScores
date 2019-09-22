package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class ConfStandingsRaw(
    @field:Json(name = "east") val east: List<StandingRaw>,
    @field:Json(name = "west") val west: List<StandingRaw>
)