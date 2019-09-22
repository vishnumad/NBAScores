package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.GameTeamScore
import io.github.vishnumad.nbascores.remote.entities.GameTeamScoreRaw
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class GameTeamScoreRawMapper @Inject constructor() : Function<GameTeamScoreRaw, GameTeamScore> {

    override fun apply(teamScore: GameTeamScoreRaw): GameTeamScore {
        return GameTeamScore(
                teamID = teamScore.teamID,
                tricode = teamScore.tricode,
                score = teamScore.score,
                losses = teamScore.loss,
                wins = teamScore.win,
                seriesLosses = teamScore.seriesLoss,
                seriesWins = teamScore.seriesWin
        )
    }
}
