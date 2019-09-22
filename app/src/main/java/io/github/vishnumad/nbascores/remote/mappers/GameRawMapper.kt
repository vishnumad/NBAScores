package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbGame
import io.github.vishnumad.nbascores.remote.entities.BroadcasterRaw
import io.github.vishnumad.nbascores.remote.entities.BroadcastersRaw
import io.github.vishnumad.nbascores.remote.entities.GameRaw
import io.github.vishnumad.nbascores.utils.DateTimeHelper
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class GameRawMapper @Inject constructor(
    private val teamScoreMapper: GameTeamScoreRawMapper,
    private val dateTimeHelper: DateTimeHelper
) : Function<GameRaw, DbGame> {

    override fun apply(game: GameRaw): DbGame {
        return DbGame(
            gameId = game.gameID,
            startTimeUTC = game.startTimeUTC,
            startDate = game.startDateEastern,
            seasonYear = game.seasonYear,
            seasonStage = game.seasonStage,
            isStartTimeTBD = game.isStartTimeTBD,
            status = game.status,
            gameClock = game.gameClock,
            arena = game.arena.name,
            arenaCity = game.arena.city,
            broadcasters = getBroadcasters(game.watch.broadcast.broadcasters),
            homeTeam = teamScoreMapper.apply(game.homeTeamScore),
            awayTeam = teamScoreMapper.apply(game.awayTeamScore),
            label = getLabel(game.status, game)
        )
    }

    private fun getLabel(status: Int, game: GameRaw): String {
        return when (status) {
            // Pre-game
            1 -> {
                if (game.isStartTimeTBD) {
                    "TBD"
                } else {
                    dateTimeHelper.convertUTCtoLocal(game.startTimeUTC)
                }
            }
            // Ongoing game
            2 -> {
                val period = game.period
                val quarter = if (period.current > period.maxRegular) {
                    "OT${period.current - period.maxRegular}"
                } else {
                    "Q${period.current}"
                }
                when {
                    period.isHalftime -> "Halftime"
                    period.isEndOfPeriod -> "End of $quarter"
                    else -> "$quarter — ${game.gameClock}"
                }
            }
            // Ongoing game
            3 -> "Final"

            else -> throw IllegalArgumentException("Invalid status: $status")
        }
    }

    private fun getBroadcasters(broadcasters: BroadcastersRaw): String {
        val list = mutableListOf<BroadcasterRaw>()

        list.addAll(broadcasters.national)
        list.addAll(broadcasters.away)
        list.addAll(broadcasters.home)

        return list.joinToString(separator = ", ") { it.shortName }
    }
}