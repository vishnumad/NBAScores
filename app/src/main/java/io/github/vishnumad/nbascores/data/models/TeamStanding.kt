package io.github.vishnumad.nbascores.data.models

data class TeamStanding(
        val id: Long,
        val name: String,
        val record: String,
        val winPct: String,
        val gamesBack: String,
        val seed: String,
        val sortKey: Int,
        val playoffsStatus: String
)