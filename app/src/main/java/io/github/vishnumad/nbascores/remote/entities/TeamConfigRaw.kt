package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class TeamConfigRaw(
    @field:Json(name = "teamId") val teamID: Long,
    @field:Json(name = "tricode") val tricode: String,
    @field:Json(name = "primaryColor") val primaryColor: String
)