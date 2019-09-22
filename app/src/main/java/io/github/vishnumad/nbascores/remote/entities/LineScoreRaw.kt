package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class LineScoreRaw(
    @field:Json(name = "tid") val teamId: String,
    @field:Json(name = "tn") val teamName: String,
    @field:Json(name = "tc") val teamCity: String,
    @field:Json(name = "ta") val teamAbbr: String,
    @field:Json(name = "s") val score: Int,
    val q1: Int,
    val q2: Int,
    val q3: Int,
    val q4: Int,
    val ot1: Int,
    val ot2: Int,
    val ot3: Int,
    val ot4: Int,
    val ot5: Int,
    val ot6: Int,
    val ot7: Int,
    val ot8: Int,
    val ot9: Int,
    val ot10: Int,
    @field:Json(name = "tstsg") val teamStats: TeamStats?,
    @field:Json(name = "pstsg") val playerStats: List<PlayerStatline>?
)
