package io.github.vishnumad.nbascores.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.ConferenceStandings
import io.github.vishnumad.nbascores.di.Injector
import io.github.vishnumad.nbascores.di.viewModelLazy
import io.github.vishnumad.nbascores.ui.main.MainNavBarFragment
import kotlinx.android.synthetic.main.standings_layout.*

class StandingsFragment : Fragment(), MainNavBarFragment {

    private val viewModel by viewModelLazy { Injector.get().standingsViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.standings_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        error_view.setOnReloadButtonClickListener {
            viewModel.onReloadButtonClick()
        }

        viewModel.getStandingsState().observe(this, Observer { viewState ->
            when (viewState) {
                StandingsViewState.Loading -> showLoading()
                is StandingsViewState.Success -> showStandings(viewState.standings)
                is StandingsViewState.Failure -> showError(viewState.message)
            }
        })
    }

    override fun onReselected() {
    }

    private fun showLoading() {
        page_content.show(loading_view)
    }

    private fun showStandings(standings: ConferenceStandings) {
        page_content.show(standings_view)
        standings_view.setStandings(standings.east, standings.west)
    }

    private fun showError(message: String) {
        page_content.show(error_view)
        error_view.setError(message)
    }
}