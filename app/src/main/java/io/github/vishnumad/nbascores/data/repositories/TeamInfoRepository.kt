package io.github.vishnumad.nbascores.data.repositories

import io.github.vishnumad.nbascores.database.daos.TeamInfoDao
import io.github.vishnumad.nbascores.database.entities.DbTeam
import io.github.vishnumad.nbascores.database.entities.DbTeamConfig
import io.github.vishnumad.nbascores.di.IO_THREAD
import io.github.vishnumad.nbascores.remote.DataApi
import io.github.vishnumad.nbascores.remote.mappers.TeamConfigRawListMapper
import io.github.vishnumad.nbascores.remote.mappers.TeamRawListMapper
import io.github.vishnumad.nbascores.remote.mappers.TeamsInfoMapper
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TeamInfoRepository @Inject constructor(
    private val dataApi: DataApi,
    private val dao: TeamInfoDao,
    private val teamsMapper: TeamRawListMapper,
    private val configsMapper: TeamConfigRawListMapper,
    private val teamsInfoMapper: TeamsInfoMapper,
    @Named(IO_THREAD) private val bgScheduler: Scheduler
) {

    fun fetchTeamsInfo(seasonYear: String): Completable {
        return Single
            .zip(
                fetchTeams(seasonYear),
                fetchTeamsConfig(seasonYear),
                teamsInfoMapper
            )
            .doOnSuccess { teamsInfo ->
                dao.saveTeamConfigs(teamsInfo.configs)
                dao.saveTeams(teamsInfo.teams)
            }
            .ignoreElement()
            .subscribeOn(bgScheduler)
    }

    fun hasTeamInfo(seasonYear: String): Boolean {
        return dao.teamInfoCount(seasonYear) > 0
    }

    private fun fetchTeams(seasonYear: String): Single<List<DbTeam>> {
        return Single
            .zip(
                Single.just(seasonYear), // emit season year into the stream
                dataApi.fetchTeams(seasonYear),
                teamsMapper
            )
    }

    private fun fetchTeamsConfig(seasonYear: String): Single<List<DbTeamConfig>> {
        return dataApi.fetchTeamsConfig(seasonYear)
            .map(configsMapper)
    }
}