package io.github.vishnumad.nbascores.database.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.data.models.GameDetails
import io.github.vishnumad.nbascores.data.models.GameDetails.*
import io.github.vishnumad.nbascores.data.models.GameStatus
import io.github.vishnumad.nbascores.data.models.TeamStatline
import io.github.vishnumad.nbascores.database.entities.DbGameDetails
import io.github.vishnumad.nbascores.remote.entities.TeamStats
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class GameDetailsMapper @Inject constructor(
    private val teamStatsMapper: TeamStatlineMapper
) : Function<DbGameDetails, GameDetails> {

    override fun apply(dbModel: DbGameDetails): GameDetails {
        return when (GameStatus.fromInt(dbModel.status)) {
            GameStatus.PRE -> getPreGameDetails(dbModel)
            GameStatus.ONGOING -> getOngoingGameDetails(dbModel)
            GameStatus.FINAL -> getPostGameDetails(dbModel)
        }
    }

    private fun getPreGameDetails(details: DbGameDetails): PreGameDetails {
        return PreGameDetails(
            arenaName = details.arenaName,
            city = details.arenaCity,
            state = details.arenaState
        )
    }

    private fun getOngoingGameDetails(details: DbGameDetails): OngoingGameDetails {
        requireNotNull(details.lastPlay)
        requireNotNull(details.homePlayerStats)
        requireNotNull(details.awayPlayerStats)
        requireNotNull(details.homeStats)
        requireNotNull(details.awayStats)

        return OngoingGameDetails(
            playClock = details.lastPlay.clock,
            playDescription = details.lastPlay.description,
            awayTeamName = details.awayTeamName,
            homeTeamName = details.homeTeamName,
            homePlayerStats = details.homePlayerStats,
            awayPlayerStats = details.awayPlayerStats,
            homeTeamStats = getTeamStats(details.homeStats),
            awayTeamStats = getTeamStats(details.awayStats)
        )
    }

    private fun getPostGameDetails(details: DbGameDetails): PostGameDetails {
        requireNotNull(details.homePlayerStats)
        requireNotNull(details.awayPlayerStats)
        requireNotNull(details.homeStats)
        requireNotNull(details.awayStats)

        return PostGameDetails(
            awayTeamName = details.awayTeamName,
            homeTeamName = details.homeTeamName,
            homePlayerStats = details.homePlayerStats,
            awayPlayerStats = details.awayPlayerStats,
            homeTeamStats = getTeamStats(details.homeStats),
            awayTeamStats = getTeamStats(details.awayStats)
        )
    }

    private fun getTeamStats(teamStats: TeamStats): TeamStatline {
        return teamStatsMapper.apply(teamStats)
    }
}