package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbStanding
import io.github.vishnumad.nbascores.database.entities.Standings
import io.github.vishnumad.nbascores.remote.entities.StandardStandingsRaw
import io.github.vishnumad.nbascores.remote.entities.StandingRaw
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class StandardStandingsRawMapper @Inject constructor() : Function<StandardStandingsRaw, Standings> {

    override fun apply(standardStandings: StandardStandingsRaw): Standings {
        return Standings(
            seasonYear = standardStandings.seasonYear,
            west = standardStandings.conference.west.map { toDatabaseModel("west", it) },
            east = standardStandings.conference.east.map { toDatabaseModel("east", it) }
        )
    }

    private fun toDatabaseModel(conference: String, standing: StandingRaw): DbStanding {
        return DbStanding(
            teamID = standing.teamID,
            awayRecord = "${standing.awayWin}-${standing.awayLoss}",
            homeRecord = "${standing.homeWin}-${standing.homeLoss}",
            clinchedPlayoffs = standing.clinchedPlayoffs,
            conference = conference,
            gamesBack = standing.gamesBack,
            last10 = "${standing.last10Win}-${standing.last10Loss}",
            record = "${standing.win}-${standing.loss}",
            sortKey = standing.sortKey.default,
            streak = "${if (standing.isWinStreak) "W" else "L"}${standing.streak}", // ex. W5 or L3
            winPct = standing.winPct
        )
    }
}