package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class StandingRaw(
        @field:Json(name = "teamId") val teamID: Long,
        @field:Json(name = "win") val win: String,
        @field:Json(name = "loss") val loss: String,
        @field:Json(name = "winPct") val winPct: String,
        @field:Json(name = "gamesBehind") val gamesBack: String,
        @field:Json(name = "clinchedPlayoffsCodeV2") val clinchedPlayoffs: String,
        @field:Json(name = "homeWin") val homeWin: String,
        @field:Json(name = "homeLoss") val homeLoss: String,
        @field:Json(name = "awayWin") val awayWin: String,
        @field:Json(name = "awayLoss") val awayLoss: String,
        @field:Json(name = "lastTenWin") val last10Win: String,
        @field:Json(name = "lastTenLoss") val last10Loss: String,
        @field:Json(name = "streak") val streak: Int,
        @field:Json(name = "isWinStreak") val isWinStreak: Boolean,
        @field:Json(name = "sortKey") val sortKey: StandingSortKeyRaw
)