package io.github.vishnumad.nbascores.ui.details

import io.github.vishnumad.nbascores.data.models.GameDetails

sealed class GameDetailsViewState {
    object Loading : GameDetailsViewState()
    class Success(val details: GameDetails) : GameDetailsViewState()
    class Failure(val message: String) : GameDetailsViewState()
}