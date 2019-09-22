package io.github.vishnumad.nbascores.data.repositories

import io.github.vishnumad.nbascores.data.models.GameDetails
import io.github.vishnumad.nbascores.database.daos.GameDao
import io.github.vishnumad.nbascores.database.daos.GameDetailsDao
import io.github.vishnumad.nbascores.database.entities.GameDetailsWrapper
import io.github.vishnumad.nbascores.database.mappers.GameDetailsMapper
import io.github.vishnumad.nbascores.di.IO_THREAD
import io.github.vishnumad.nbascores.di.MAIN_THREAD
import io.github.vishnumad.nbascores.remote.DataApi
import io.github.vishnumad.nbascores.remote.mappers.GameDetailsRawMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class GameDetailsRepository @Inject constructor(
    private val dataApi: DataApi,
    private val detailsDao: GameDetailsDao,
    private val gameDao: GameDao,
    private val rawMapper: GameDetailsRawMapper,
    private val detailsMapper: GameDetailsMapper,
    @Named(IO_THREAD) private val bgScheduler: Scheduler,
    @Named(MAIN_THREAD) private val mainScheduler: Scheduler
) {

    fun fetchGameDetails(seasonYear: String, gameId: String): Completable {
        return dataApi.fetchGameDetails(seasonYear, gameId)
            .map(rawMapper)
            .flatMapCompletable { wrapper -> saveDetails(wrapper, gameId) }
            .subscribeOn(bgScheduler)
            .observeOn(mainScheduler)
    }

    private fun saveDetails(wrapper: GameDetailsWrapper, gameId: String): Completable {
        return Completable
            .fromAction {
                gameDao.updateScore(gameId, wrapper.label, wrapper.homeScore, wrapper.awayScore)
                detailsDao.saveGameDetails(wrapper.details)
            }
            .subscribeOn(bgScheduler)
    }

    fun getGameDetails(gameId: String): Observable<GameDetails> {
        return detailsDao.getGameDetails(gameId)
            .map(detailsMapper)
            .observeOn(mainScheduler)
    }
}