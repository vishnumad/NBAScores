package io.github.vishnumad.nbascores.database.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.data.models.ScheduledGame
import io.github.vishnumad.nbascores.database.entities.GameReadModel
import io.github.vishnumad.nbascores.utils.DateTimeHelper
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class ScheduledGameMapper @Inject constructor(
    private val dateTimeHelper: DateTimeHelper
) : Function<GameReadModel, ScheduledGame> {

    override fun apply(model: GameReadModel): ScheduledGame {
        return ScheduledGame(
            gameId = model.game.gameId,
            seasonYear = model.game.seasonYear,
            startTime = getStartTime(model.game.startTimeUTC, model.game.isStartTimeTBD),
            arena = "${model.game.arena}, ${model.game.arenaCity}",
            broadcasters = model.game.broadcasters,
            awayAbbrev = model.game.awayTeam.tricode,
            awayName = model.awayName,
            awayCity = model.awayCity,
            awayColor = model.awayColor,
            homeAbbrev = model.game.homeTeam.tricode,
            homeName = model.homeName,
            homeCity = model.homeCity,
            homeColor = model.homeColor
        )
    }

    private fun getStartTime(time: String, isTBD: Boolean): String {
        when {
            isTBD -> return "TBD"
            else -> return dateTimeHelper.convertUTCtoLocal(time)
        }
    }
}