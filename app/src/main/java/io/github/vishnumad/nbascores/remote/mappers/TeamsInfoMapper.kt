package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbTeam
import io.github.vishnumad.nbascores.database.entities.DbTeamConfig
import io.github.vishnumad.nbascores.database.entities.TeamsInfo
import io.reactivex.functions.BiFunction
import javax.inject.Inject

@Reusable
class TeamsInfoMapper @Inject constructor() :
    BiFunction<List<DbTeam>, List<DbTeamConfig>, TeamsInfo> {

    override fun apply(teams: List<DbTeam>, configs: List<DbTeamConfig>): TeamsInfo {
        return TeamsInfo(teams, configs)
    }
}