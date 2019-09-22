package io.github.vishnumad.nbascores.database.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.data.models.ScheduledGame
import io.github.vishnumad.nbascores.database.entities.GameReadModel
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class ScheduledGameListMapper @Inject constructor(
    private val scheduledGameMapper: ScheduledGameMapper
) : Function<List<GameReadModel>, List<ScheduledGame>> {

    override fun apply(models: List<GameReadModel>): List<ScheduledGame> {
        return models.map { scheduledGameMapper.apply(it) }
    }
}