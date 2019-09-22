package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.GamePeriod
import io.github.vishnumad.nbascores.remote.entities.GamePeriodRaw
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class GamePeriodRawMapper @Inject constructor() : Function<GamePeriodRaw, GamePeriod> {

    override fun apply(period: GamePeriodRaw): GamePeriod {
        return GamePeriod(
            current = period.current,
            type = period.type,
            maxRegular = period.maxRegular,
            isHalftime = period.isHalftime,
            isEndOfPeriod = period.isEndOfPeriod
        )
    }
}
