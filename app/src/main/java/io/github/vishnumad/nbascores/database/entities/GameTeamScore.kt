package io.github.vishnumad.nbascores.database.entities

data class GameTeamScore(
    val teamID: Long,
    val tricode: String,
    val wins: String,
    val losses: String,
    val seriesWins: String,
    val seriesLosses: String,
    val score: String
)