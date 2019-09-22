package io.github.vishnumad.nbascores.data.repositories

import io.github.vishnumad.nbascores.data.exceptions.NoStandingsException
import io.github.vishnumad.nbascores.data.models.ConferenceStandings
import io.github.vishnumad.nbascores.data.models.TeamStanding
import io.github.vishnumad.nbascores.database.daos.StandingsDao
import io.github.vishnumad.nbascores.database.mappers.StandingReadModelListMapper
import io.github.vishnumad.nbascores.di.IO_THREAD
import io.github.vishnumad.nbascores.di.MAIN_THREAD
import io.github.vishnumad.nbascores.remote.DataApi
import io.github.vishnumad.nbascores.remote.mappers.StandardStandingsRawMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Named

private const val CONF_EAST = "east"
private const val CONF_WEST = "west"

class StandingsRepository @Inject constructor(
        private val dataApi: DataApi,
        private val dao: StandingsDao,
        private val rawMapper: StandardStandingsRawMapper,
        private val readModelMapper: StandingReadModelListMapper,
        private val teamInfoRepo: TeamInfoRepository,
        @Named(IO_THREAD) private val bgScheduler: Scheduler,
        @Named(MAIN_THREAD) private val mainScheduler: Scheduler
) {

    fun fetchStandings(): Completable {
        return dataApi.fetchConferenceStandings()
            .map(rawMapper)
            .doOnSuccess { standings ->
                if (standings.east.isEmpty() || standings.west.isEmpty())
                    throw NoStandingsException()

                dao.saveStandings(standings.east)
                dao.saveStandings(standings.west)
            }
            .flatMapCompletable { standings ->
                if (teamInfoRepo.hasTeamInfo(standings.seasonYear)) {
                    return@flatMapCompletable Completable.complete()
                } else {
                    return@flatMapCompletable teamInfoRepo
                        .fetchTeamsInfo(standings.seasonYear)
                }
            }
            .subscribeOn(bgScheduler)
            .observeOn(mainScheduler)
    }

    fun getConferenceStandings(): Observable<ConferenceStandings> {
        return Observable
            .zip(
                    getStandings(CONF_EAST),
                    getStandings(CONF_WEST),
                    BiFunction { east: List<TeamStanding>, west: List<TeamStanding> ->
                        return@BiFunction ConferenceStandings(east, west)
                    }
            )
            .observeOn(mainScheduler)
    }

    fun getStandings(conference: String): Observable<List<TeamStanding>> {
        return dao.getConferenceStandings(conference)
            .map(readModelMapper)
            .observeOn(mainScheduler)
    }
}