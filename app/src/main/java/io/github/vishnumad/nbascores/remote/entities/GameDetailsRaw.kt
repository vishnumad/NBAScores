package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class GameDetailsRaw(
    @field:Json(name = "mid") val mid: String,
    @field:Json(name = "gid") val gameId: String,
    @field:Json(name = "etm") val dateTimeET: String,
    @field:Json(name = "ac") val arenaCity: String,
    @field:Json(name = "as") val arenaState: String,
    @field:Json(name = "an") val arenaName: String,
    @field:Json(name = "p") val period: Int?,
    @field:Json(name = "st") val status: Int,
    @field:Json(name = "stt") val statusText: String,
    @field:Json(name = "cl") val clock: String,
    @field:Json(name = "lpla") val lastPlay: LastPlayRaw?,
    @field:Json(name = "vls") val awayLineScore: LineScoreRaw,
    @field:Json(name = "hls") val homeLineScore: LineScoreRaw,
    @field:Json(name = "offs") val referees: RefereesRaw
)