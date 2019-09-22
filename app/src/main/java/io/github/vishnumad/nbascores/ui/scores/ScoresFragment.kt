package io.github.vishnumad.nbascores.ui.scores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.di.Injector
import io.github.vishnumad.nbascores.di.viewModelLazy
import io.github.vishnumad.nbascores.ui.details.GameDetailsActivity
import io.github.vishnumad.nbascores.ui.main.MainNavBarFragment
import io.github.vishnumad.nbascores.utils.logDebug
import kotlinx.android.synthetic.main.scores_layout.*

class ScoresFragment : Fragment(), MainNavBarFragment, ScoresView.Listener {

    private val viewModel by viewModelLazy { Injector.get().scoresViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.scores_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        scores_view.setListener(this)
        error_view.setOnReloadButtonClickListener {
            viewModel.onReloadButtonClick()
        }

        viewModel.getScoresState().observe(this, Observer { viewState ->
            when (viewState) {
                is ScoresViewState.Loading -> showLoading()
                is ScoresViewState.Success -> showScores(viewState.scores, viewState.diff)
                is ScoresViewState.Empty -> showEmpty()
                is ScoresViewState.Failure -> showError(viewState.errorMessage)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onSwipeRefresh() {
        viewModel.onSwipeRefresh()
    }

    override fun onScoreClick(gameId: String, seasonYear: String) {
        logDebug("Selected Item ID: $gameId")
        GameDetailsActivity.open(requireActivity(), gameId, seasonYear)
    }

    override fun onReselected() {
    }

    private fun showLoading() {
        page_content.show(loading_view)
    }

    private fun showScores(scores: List<LiveScore>, diffResult: DiffUtil.DiffResult) {
        page_content.show(scores_view)
        scores_view.updateScores(scores, diffResult)
    }

    private fun showEmpty() {
        page_content.show(error_view)
        error_view.setError(getString(R.string.empty_games_message))
    }

    private fun showError(message: String) {
        page_content.show(error_view)
        error_view.setError(message)
    }
}