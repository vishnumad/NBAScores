package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class GameRaw(
        @field:Json(name = "seasonStageId") val seasonStage: Int,
        @field:Json(name = "seasonYear") val seasonYear: String,
        @field:Json(name = "gameId") val gameID: String,
        @field:Json(name = "startDateEastern") val startDateEastern: String,
        @field:Json(name = "clock") val gameClock: String,
        @field:Json(name = "arena") val arena: GameArenaRaw,
        @field:Json(name = "watch") val watch: WatchRaw,
        @field:Json(name = "statusNum") val status: Int,
        @field:Json(name = "startTimeUTC") val startTimeUTC: String,
        @field:Json(name = "isStartTimeTBD") val isStartTimeTBD: Boolean,
        @field:Json(name = "period") val period: GamePeriodRaw,
        @field:Json(name = "hTeam") val homeTeamScore: GameTeamScoreRaw,
        @field:Json(name = "vTeam") val awayTeamScore: GameTeamScoreRaw
)