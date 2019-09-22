package io.github.vishnumad.nbascores.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.github.vishnumad.nbascores.data.models.GameDetails
import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.data.repositories.GameDetailsRepository
import io.github.vishnumad.nbascores.data.repositories.LiveScoresRepository
import io.github.vishnumad.nbascores.di.MAIN_THREAD
import io.github.vishnumad.nbascores.ui.common.Polling
import io.github.vishnumad.nbascores.utils.Event
import io.github.vishnumad.nbascores.utils.logError
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import javax.inject.Named

private const val REFRESH_INTERVAL_MS: Long = 20 * 1000

class GameDetailsViewModel @AssistedInject constructor(
    @Assisted private val gameId: String,
    @Assisted private val seasonYear: String,
    private val scoresRepo: LiveScoresRepository,
    private val detailsRepo: GameDetailsRepository,
    @Named(MAIN_THREAD) private val mainScheduler: Scheduler
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(gameId: String, seasonYear: String): GameDetailsViewModel
    }

    private val scoreState = MutableLiveData<LiveScore>()
    private val detailsState = MutableLiveData<GameDetailsViewState>()

    private val toastEvents = MutableLiveData<Event<String>>()

    private val detailsPolling: Polling

    private val disposables = CompositeDisposable()

    init {
        detailsPolling = Polling(
            intervalMs = REFRESH_INTERVAL_MS,
            initialDelay = REFRESH_INTERVAL_MS,
            task = detailsRepo.fetchGameDetails(seasonYear, gameId),
            observeScheduler = mainScheduler,
            onPollError = { handleError(it) }
        )

        // Subscribe to live score from database
        disposables += scoresRepo.getScore(gameId)
            .subscribeBy { scoreState.value = it }

        disposables += detailsRepo.getGameDetails(gameId)
            .subscribeBy(
                onNext = { details ->
                    detailsState.value = GameDetailsViewState.Success(details)

                    // Only poll if this is an ongoing game
                    if (details is GameDetails.OngoingGameDetails) {
                        detailsPolling.enable()
                    } else {
                        detailsPolling.disable()
                    }
                },
                onError = { error ->
                    handleError(error)
                }
            )

        detailsState.value = GameDetailsViewState.Loading
        fetchGameDetails()
    }

    fun getScoreState(): LiveData<LiveScore> = scoreState

    fun getDetailsState(): LiveData<GameDetailsViewState> = detailsState

    fun getToastEvents(): LiveData<Event<String>> = toastEvents

    fun onStart() {
        detailsPolling.start()
    }

    fun onStop() {
        detailsPolling.stop()
    }

    fun onSwipeRefresh() {
        fetchGameDetails()
    }

    fun onReloadButtonClick() {
        detailsState.value = GameDetailsViewState.Loading
        fetchGameDetails()
    }

    private fun fetchGameDetails() {
        disposables += detailsRepo.fetchGameDetails(seasonYear, gameId)
            .subscribeBy(onError = { handleError(it) })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun errorMessageFor(error: Throwable): String {
        when (error) {
            is HttpException -> {
                return buildString {
                    append("Error ${error.code()}")
                    if (error.message().isNotEmpty()) {
                        append(": ${error.message()}")
                    }
                }
            }
            else -> {
                return "Could not load game details"
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        logError(throwable)
        val message = errorMessageFor(throwable)
        if (detailsState.value is GameDetailsViewState.Success) {
            // Show toast instead of error page
            toastEvents.value = Event(message)
        } else {
            detailsState.value = GameDetailsViewState.Failure(message)
        }
    }
}