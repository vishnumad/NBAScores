package io.github.vishnumad.nbascores.database.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.database.entities.GameReadModel
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class LiveScoreListMapper @Inject constructor(
    private val liveScoreMapper: LiveScoreMapper
) : Function<List<GameReadModel>, List<LiveScore>> {

    override fun apply(models: List<GameReadModel>): List<LiveScore> {
        return models.map(liveScoreMapper::apply)
    }
}