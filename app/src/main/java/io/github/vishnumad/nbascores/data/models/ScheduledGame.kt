package io.github.vishnumad.nbascores.data.models

data class ScheduledGame(
    val gameId: String,
    val seasonYear: String,
    val startTime: String,
    val arena: String,
    val broadcasters: String,
    val homeAbbrev: String,
    val homeCity: String,
    val homeName: String,
    val homeColor: String,
    val awayAbbrev: String,
    val awayCity: String,
    val awayName: String,
    val awayColor: String
)