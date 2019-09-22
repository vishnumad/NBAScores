package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbGameDetails
import io.github.vishnumad.nbascores.database.entities.GameDetailsWrapper
import io.github.vishnumad.nbascores.database.entities.LastPlay
import io.github.vishnumad.nbascores.remote.entities.GameDetailsRaw
import io.github.vishnumad.nbascores.remote.entities.LastPlayRaw
import io.github.vishnumad.nbascores.utils.DateTimeHelper
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class GameDetailsRawMapper @Inject constructor(
    private val dateTimeHelper: DateTimeHelper
) : Function<GameDetailsRaw, GameDetailsWrapper> {

    override fun apply(details: GameDetailsRaw): GameDetailsWrapper {
        return GameDetailsWrapper(
            details = getGameDetails(details),
            label = getLabel(details),
            homeScore = getScore(details.homeLineScore.score, details.status),
            awayScore = getScore(details.awayLineScore.score, details.status)
        )
    }

    private fun getScore(score: Int, status: Int): String {
        return when (status) {
            1 -> "" // Pre-game; No score
            else -> "$score"
        }
    }

    private fun getLabel(details: GameDetailsRaw): String {
        return when (details.status) {
            1 -> dateTimeHelper.convertETtoLocal(details.dateTimeET) // Pre-game; show start time
            else -> details.statusText
        }
    }

    private fun getGameDetails(details: GameDetailsRaw): DbGameDetails {
        return DbGameDetails(
            gameId = details.gameId,
            status = details.status,
            homeId = details.homeLineScore.teamId,
            awayId = details.awayLineScore.teamId,
            homeStats = details.homeLineScore.teamStats,
            awayStats = details.awayLineScore.teamStats,
            lastPlay = getLastPlay(details.lastPlay),
            awayPlayerStats = details.awayLineScore.playerStats,
            homePlayerStats = details.homeLineScore.playerStats,
            homeTeamName = "${details.homeLineScore.teamCity} ${details.homeLineScore.teamName}",
            awayTeamName = "${details.awayLineScore.teamCity} ${details.awayLineScore.teamName}",
            arenaName = details.arenaName,
            arenaCity = details.arenaCity,
            arenaState = details.arenaState
        )
    }

    private fun getLastPlay(lastPlayRaw: LastPlayRaw?): LastPlay? {
        if (lastPlayRaw == null) return null

        return LastPlay(
            description = lastPlayRaw.description,
            clock = lastPlayRaw.clock,
            teamId = lastPlayRaw.tid
        )
    }
}