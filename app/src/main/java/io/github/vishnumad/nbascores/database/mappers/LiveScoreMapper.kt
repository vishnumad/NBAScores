package io.github.vishnumad.nbascores.database.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.data.models.GameStatus
import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.database.entities.GameReadModel
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class LiveScoreMapper @Inject constructor() : Function<GameReadModel, LiveScore> {

    override fun apply(model: GameReadModel): LiveScore {
        return LiveScore(
                gameId = model.game.gameId,
                seasonYear = model.game.seasonYear,
                status = GameStatus.fromInt(model.game.status),
                mainLabel = model.game.label,
                secondaryLabel = "${model.game.arena}, ${model.game.arenaCity}",
                awayAbbrev = model.game.awayTeam.tricode,
                awayName = model.awayName,
                awayCity = model.awayCity,
                awayColor = model.awayColor,
                awayScore = model.game.awayTeam.score,
                homeAbbrev = model.game.homeTeam.tricode,
                homeName = model.homeName,
                homeCity = model.homeCity,
                homeColor = model.homeColor,
                homeScore = model.game.homeTeam.score
        )
    }

}