package io.github.vishnumad.nbascores.data.repositories

import io.github.vishnumad.nbascores.data.models.ScheduledGame
import io.github.vishnumad.nbascores.database.mappers.ScheduledGameListMapper
import io.reactivex.Completable
import io.reactivex.Observable
import org.threeten.bp.LocalDate
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val gamesRepo: GamesRepository,
    private val scheduleMapper: ScheduledGameListMapper
) {

    fun fetchSchedule(date: LocalDate): Completable {
        return gamesRepo.fetchGames(date)
    }

    fun getScheduledGames(date: LocalDate): Observable<List<ScheduledGame>> {
        return gamesRepo.getGames(date)
            .map(scheduleMapper)
    }
}