package io.github.vishnumad.nbascores.ui.scores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.vishnumad.nbascores.data.exceptions.NoGamesException
import io.github.vishnumad.nbascores.data.models.GameStatus
import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.data.repositories.LiveScoresRepository
import io.github.vishnumad.nbascores.di.DATE_ET
import io.github.vishnumad.nbascores.di.MAIN_THREAD
import io.github.vishnumad.nbascores.ui.common.Polling
import io.github.vishnumad.nbascores.utils.DiffFunction
import io.github.vishnumad.nbascores.utils.RxDiffUtil
import io.github.vishnumad.nbascores.utils.logError
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Named

private const val POLL_INTERVAL_MS: Long = 20 * 1000

class ScoresViewModel @Inject constructor(
    private val gamesRepo: LiveScoresRepository,
    @Named(DATE_ET) private val date: LocalDate,
    @Named(MAIN_THREAD) private val mainScheduler: Scheduler
) : ViewModel() {

    private val scoresState: MutableLiveData<ScoresViewState> = MutableLiveData()
    private val scoresPolling: Polling

    private val disposables = CompositeDisposable()

    init {
        scoresPolling = Polling(
            intervalMs = POLL_INTERVAL_MS,
            task = gamesRepo.fetchScores(date),
            observeScheduler = mainScheduler,
            onPollError = { handleError(it) }
        )

        // Observe scores from the database
        disposables += gamesRepo.getScores(date)
            .doOnNext {
                if (it.isEmpty())
                    scoresState.value = ScoresViewState.Loading
            }
            .filter { it.isNotEmpty() } // Ignore empty list that gets emitted by room
            .compose(liveScoreDiffer())
            .subscribeBy { (scores, diff) ->
                scoresState.value = ScoresViewState.Success(scores, diff)

                if (hasOngoingGames(scores)) {
                    if (!scoresPolling.isPolling())
                        scoresPolling.enable()
                } else {
                    scoresPolling.disable()
                }
            }
    }

    fun getScoresState(): LiveData<ScoresViewState> = scoresState

    fun onStart() {
        scoresPolling.start()
    }

    fun onStop() {
        scoresPolling.stop()
    }

    fun onSwipeRefresh() {
        fetchScores()
    }

    fun onReloadButtonClick() {
        scoresState.value = ScoresViewState.Loading
        fetchScores()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun fetchScores() {
        disposables += gamesRepo.fetchScores(date)
            .subscribeBy(onError = { handleError(it) })
    }

    private fun liveScoreDiffer() =
        RxDiffUtil.calculateDiff(DiffFunction<LiveScore> { old, new ->
            LiveScoreDiffer(old, new)
        })

    private fun hasOngoingGames(scores: List<LiveScore>): Boolean {
        return scores.any { it.status == GameStatus.ONGOING }
    }

    private fun handleError(error: Throwable) {
        scoresState.value = when (error) {
            is NoGamesException -> ScoresViewState.Empty
            else -> {
                logError(error)
                ScoresViewState.Failure("Could not load scores for this date")
            }
        }
    }
}