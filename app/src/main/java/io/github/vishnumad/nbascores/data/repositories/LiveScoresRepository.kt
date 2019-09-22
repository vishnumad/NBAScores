package io.github.vishnumad.nbascores.data.repositories

import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.database.mappers.LiveScoreListMapper
import io.github.vishnumad.nbascores.database.mappers.LiveScoreMapper
import io.reactivex.Completable
import io.reactivex.Observable
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiveScoresRepository @Inject constructor(
        private val gamesRepo: GamesRepository,
        private val liveScoresMapper: LiveScoreListMapper,
        private val liveScoreMapper: LiveScoreMapper
) {

    fun fetchScores(date: LocalDate): Completable {
        return gamesRepo.fetchGames(date)
    }

    fun getScores(date: LocalDate): Observable<List<LiveScore>> {
        return gamesRepo.getGames(date)
            .map(liveScoresMapper)
    }

    fun getScore(id: String): Observable<LiveScore> {
        return gamesRepo.getGame(id)
            .map(liveScoreMapper)
    }
}