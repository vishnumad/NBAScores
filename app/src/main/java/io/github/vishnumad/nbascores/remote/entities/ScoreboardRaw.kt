package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class ScoreboardRaw(
    @field:Json(name = "numGames") val numGames: Int,
    @field:Json(name = "games") val games: List<GameRaw>
)