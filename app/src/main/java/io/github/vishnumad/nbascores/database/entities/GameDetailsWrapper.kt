package io.github.vishnumad.nbascores.database.entities

data class GameDetailsWrapper(
    val details: DbGameDetails,
    val homeScore: String,
    val awayScore: String,
    val label: String
)