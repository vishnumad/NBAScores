package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbTeam
import io.github.vishnumad.nbascores.remote.entities.TeamRaw
import io.reactivex.functions.BiFunction
import javax.inject.Inject

@Reusable
class TeamRawListMapper @Inject constructor() : BiFunction<String, List<TeamRaw>, List<DbTeam>> {

    override fun apply(seasonYear: String, teams: List<TeamRaw>): List<DbTeam> {
        return teams.map { toLocalModel(seasonYear, it) }
    }

    private fun toLocalModel(seasonYear: String, team: TeamRaw): DbTeam {
        return DbTeam(
                teamID = team.teamId,
                city = team.city,
                confName = team.confName,
                divName = team.divName,
                fullName = team.fullName,
                nickname = team.nickname,
                tricode = team.tricode,
                urlName = team.urlName,
                seasonYear = seasonYear
        )
    }
}