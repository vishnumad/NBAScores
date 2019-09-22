package io.github.vishnumad.nbascores.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.vishnumad.nbascores.remote.entities.PlayerStatline
import io.github.vishnumad.nbascores.remote.entities.TeamStats

@Entity
data class DbGameDetails(
    @PrimaryKey val gameId: String,
    @Embedded(prefix = "play_") val lastPlay: LastPlay?,
    @Embedded(prefix = "home_") val homeStats: TeamStats?,
    @Embedded(prefix = "away_") val awayStats: TeamStats?,
    val status: Int,
    val homePlayerStats: List<PlayerStatline>?,
    val awayPlayerStats: List<PlayerStatline>?,
    val arenaName: String,
    val arenaCity: String,
    val arenaState: String,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeId: String,
    val awayId: String
)