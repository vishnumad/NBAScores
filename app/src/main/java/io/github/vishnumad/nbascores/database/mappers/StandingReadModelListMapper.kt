package io.github.vishnumad.nbascores.database.mappers

import io.github.vishnumad.nbascores.data.models.TeamStanding
import io.github.vishnumad.nbascores.database.entities.StandingReadModel
import io.reactivex.functions.Function
import javax.inject.Inject

class StandingReadModelListMapper @Inject constructor() :
    Function<List<StandingReadModel>, List<TeamStanding>> {

    override fun apply(standings: List<StandingReadModel>): List<TeamStanding> {
        return standings.mapIndexed { index, model -> toViewModel(model, index + 1) }
    }

    private fun toViewModel(standing: StandingReadModel, seed: Int): TeamStanding {
        return TeamStanding(
            id = standing.dbStanding.teamID,
            name = standing.fullName,
            record = standing.dbStanding.record,
            winPct = standing.dbStanding.winPct,
            gamesBack = standing.dbStanding.gamesBack,
            seed = seed.toString(),
            sortKey = standing.dbStanding.sortKey,
            playoffsStatus = standing.dbStanding.clinchedPlayoffs
        )
    }
}