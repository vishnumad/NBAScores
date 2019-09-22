package io.github.vishnumad.nbascores.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbGame(
    @PrimaryKey val gameId: String,
    val startDate: String,
    val seasonYear: String,
    val seasonStage: Int,
    val status: Int,
    val startTimeUTC: String,
    val isStartTimeTBD: Boolean,
    val gameClock: String,
    val arena: String,
    val arenaCity: String,
    val broadcasters: String,
    val label: String,
    @Embedded(prefix = "home_") val homeTeam: GameTeamScore,
    @Embedded(prefix = "away_") val awayTeam: GameTeamScore
)