package io.github.vishnumad.nbascores.remote.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.database.entities.DbTeamConfig
import io.github.vishnumad.nbascores.remote.entities.TeamConfigRaw
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class TeamConfigRawListMapper @Inject constructor() :
    Function<List<TeamConfigRaw>, List<DbTeamConfig>> {

    override fun apply(configs: List<TeamConfigRaw>): List<DbTeamConfig> {
        return configs.map(::toLocalModel)
    }

    private fun toLocalModel(config: TeamConfigRaw): DbTeamConfig {
        return DbTeamConfig(
            teamID = config.teamID,
            tricode = config.tricode,
            primaryColor = config.primaryColor
        )
    }
}