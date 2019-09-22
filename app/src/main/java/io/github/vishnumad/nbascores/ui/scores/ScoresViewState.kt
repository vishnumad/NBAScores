package io.github.vishnumad.nbascores.ui.scores

import androidx.recyclerview.widget.DiffUtil
import io.github.vishnumad.nbascores.data.models.LiveScore

sealed class ScoresViewState {
    object Loading : ScoresViewState()
    object Empty : ScoresViewState()
    class Success(val scores: List<LiveScore>, val diff: DiffUtil.DiffResult) : ScoresViewState()
    class Failure(val errorMessage: String) : ScoresViewState()
}