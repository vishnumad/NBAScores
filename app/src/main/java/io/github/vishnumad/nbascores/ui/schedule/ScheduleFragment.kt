package io.github.vishnumad.nbascores.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.ScheduledGame
import io.github.vishnumad.nbascores.di.Injector
import io.github.vishnumad.nbascores.di.viewModelLazy
import io.github.vishnumad.nbascores.ui.details.GameDetailsActivity
import io.github.vishnumad.nbascores.ui.main.MainNavBarFragment
import io.github.vishnumad.nbascores.utils.logDebug
import kotlinx.android.synthetic.main.schedule_layout.*

class ScheduleFragment : Fragment(), MainNavBarFragment,
    ScheduleView.Listener, DateSwitcherView.Listener {

    private val viewModel by viewModelLazy { Injector.get().scheduleViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.schedule_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        date_switcher_view.setListener(this)
        schedule_view.setListener(this)

        error_view.setOnReloadButtonClickListener {
            viewModel.onReloadButtonClick()
        }

        viewModel.getDateSwitcherState().observe(this, Observer { dateSwitcherState ->
            date_switcher_view.setDate(dateSwitcherState)
        })

        viewModel.getScheduleState().observe(this, Observer { state ->
            when (state) {
                ScheduleViewState.Loading -> showLoading()
                ScheduleViewState.Empty -> showEmpty()
                is ScheduleViewState.Success -> showSchedule(state.schedule)
                is ScheduleViewState.Failure -> showError(state.message)
            }
        })
    }

    override fun onReselected() {
        viewModel.resetDate()
    }

    override fun onSwipeRefresh() {
        viewModel.onSwipeRefresh()
    }

    override fun onScheduledGameClick(gameId: String, seasonYear: String) {
        logDebug("OnScheduledGameClick: $gameId, $seasonYear")
        GameDetailsActivity.open(requireActivity(), gameId, seasonYear)
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {
        viewModel.setDate(year, month, day)
    }

    override fun onNextButtonClick() {
        viewModel.nextDate()
    }

    override fun onPrevButtonClick() {
        viewModel.prevDate()
    }

    private fun showLoading() {
        page_content.show(loading_view)
        schedule_view.clearSchedule()
    }

    private fun showEmpty() {
        page_content.show(error_view)
        error_view.setError(getString(R.string.empty_games_message))
    }

    private fun showSchedule(schedule: List<ScheduledGame>) {
        page_content.show(schedule_view)
        schedule_view.updateSchedule(schedule)
    }

    private fun showError(message: String) {
        page_content.show(error_view)
        error_view.setError(message)
    }
}