package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbGame
import io.github.vishnumad.nbascores.remote.entities.ScoreboardRaw
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class ScoreboardRawMapper @Inject constructor(
        private val gameMapper: GameRawMapper
) : Function<ScoreboardRaw, List<DbGame>> {

    override fun apply(scoreboard: ScoreboardRaw): List<DbGame> {
        return scoreboard.games.map(gameMapper::apply)
    }
}