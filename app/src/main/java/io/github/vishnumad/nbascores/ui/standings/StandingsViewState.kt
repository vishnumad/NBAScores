package io.github.vishnumad.nbascores.ui.standings

import io.github.vishnumad.nbascores.data.models.ConferenceStandings

sealed class StandingsViewState {
    object Loading : StandingsViewState()
    object Empty : StandingsViewState()
    class Success(val standings: ConferenceStandings) : StandingsViewState()
    class Failure(val message: String) : StandingsViewState()
}