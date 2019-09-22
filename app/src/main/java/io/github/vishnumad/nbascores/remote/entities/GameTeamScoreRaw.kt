package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class GameTeamScoreRaw(
        @field:Json(name = "teamId") val teamID: Long,
        @field:Json(name = "triCode") val tricode: String,
        @field:Json(name = "win") val win: String,
        @field:Json(name = "loss") val loss: String,
        @field:Json(name = "seriesWin") val seriesWin: String,
        @field:Json(name = "seriesLoss") val seriesLoss: String,
        @field:Json(name = "score") val score: String
)