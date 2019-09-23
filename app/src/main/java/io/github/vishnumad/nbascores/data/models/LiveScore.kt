package io.github.vishnumad.nbascores.data.models

data class LiveScore(
    val gameId: String,
    val seasonYear: String,
    val mainLabel: String,
    val secondaryLabel: String,
    val status: GameStatus,
    val homeAbbrev: String,
    val homeCity: String,
    val homeName: String,
    val homeColor: String,
    val awayAbbrev: String,
    val awayCity: String,
    val awayName: String,
    val awayColor: String,
    val homeScore: String,
    val awayScore: String
)