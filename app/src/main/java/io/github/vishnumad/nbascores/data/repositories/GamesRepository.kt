package io.github.vishnumad.nbascores.data.repositories

import io.github.vishnumad.nbascores.data.exceptions.NoGamesException
import io.github.vishnumad.nbascores.database.daos.GameDao
import io.github.vishnumad.nbascores.database.entities.GameReadModel
import io.github.vishnumad.nbascores.di.IO_THREAD
import io.github.vishnumad.nbascores.di.ISO_FORMATTER
import io.github.vishnumad.nbascores.di.MAIN_THREAD
import io.github.vishnumad.nbascores.remote.DataApi
import io.github.vishnumad.nbascores.remote.mappers.ScoreboardRawMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class GamesRepository @Inject constructor(
    private val dataApi: DataApi,
    private val dao: GameDao,
    private val rawMapper: ScoreboardRawMapper,
    private val teamInfoRepo: TeamInfoRepository,
    @Named(ISO_FORMATTER) private val dateFormatter: DateTimeFormatter,
    @Named(IO_THREAD) private val bgScheduler: Scheduler,
    @Named(MAIN_THREAD) private val mainScheduler: Scheduler
) {

    fun fetchGames(date: LocalDate): Completable {
        val dateString = date.format(dateFormatter)
        return dataApi.fetchScoreboard(dateString)
            .map(rawMapper)
            .doOnSuccess { games ->
                if (games.isEmpty()) throw NoGamesException()
                dao.saveGames(games)
                dao.clearUnneededGames(dateString, games.map { it.gameId })
            }
            .flatMapCompletable { games ->
                val year = games[0].seasonYear
                if (teamInfoRepo.hasTeamInfo(year)) {
                    return@flatMapCompletable Completable.complete()
                } else {
                    // No team info inside the database so we fetch it
                    // from the API
                    return@flatMapCompletable teamInfoRepo.fetchTeamsInfo(year)
                }
            }
            .subscribeOn(bgScheduler)
            .observeOn(mainScheduler)
    }

    fun getGames(date: LocalDate): Observable<List<GameReadModel>> {
        return dao.getGames(date.format(dateFormatter))
            .observeOn(mainScheduler)
    }

    fun getGame(id: String): Observable<GameReadModel> {
        return dao.getGame(id)
            .observeOn(mainScheduler)
    }
}