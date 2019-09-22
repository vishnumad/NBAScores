package io.github.vishnumad.nbascores.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.vishnumad.nbascores.data.exceptions.NoStandingsException
import io.github.vishnumad.nbascores.data.repositories.StandingsRepository
import io.github.vishnumad.nbascores.utils.logError
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class StandingsViewModel @Inject constructor(
    private val standingsRepo: StandingsRepository
) : ViewModel() {

    private val standingsState = MutableLiveData<StandingsViewState>()

    private val disposables = CompositeDisposable()

    init {
        disposables += standingsRepo.getConferenceStandings()
            .subscribeBy { standings ->
                if (standings.isEmpty()) {
                    standingsState.value = StandingsViewState.Loading
                } else {
                    standingsState.value = StandingsViewState.Success(standings)
                }
            }

        fetchStandings()
    }

    fun getStandingsState(): LiveData<StandingsViewState> = standingsState

    fun onReloadButtonClick() {
        standingsState.value = StandingsViewState.Loading
        fetchStandings()
    }

    private fun fetchStandings() {
        disposables += standingsRepo.fetchStandings()
            .subscribeBy(onError = { handleError(it) })
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is NoStandingsException -> standingsState.value = StandingsViewState.Empty
            else -> {
                logError(error)
                standingsState.value = StandingsViewState.Failure("Could not load standings")
            }
        }
    }

}