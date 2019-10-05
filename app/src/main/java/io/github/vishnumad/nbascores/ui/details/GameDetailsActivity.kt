package io.github.vishnumad.nbascores.ui.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.GameDetails
import io.github.vishnumad.nbascores.di.Injector
import io.github.vishnumad.nbascores.di.activityViewModelLazy
import io.github.vishnumad.nbascores.utils.EventObserver
import kotlinx.android.synthetic.main.activity_game_details.*

class GameDetailsActivity : AppCompatActivity() {

    companion object {
        private const val GAME_ID = "io.github.vishnumad.nbascores.GAME_ID"
        private const val GAME_SEASON_YEAR = "io.github.vishnumad.nbascores.GAME_SEASON_YEAR"

        fun open(activityContext: Activity, id: String, seasonYear: String) {
            val intent = Intent(activityContext, GameDetailsActivity::class.java).apply {
                putExtra(GAME_ID, id)
                putExtra(GAME_SEASON_YEAR, seasonYear)
            }
            activityContext.startActivity(intent)
        }
    }


    val gameId: String by lazy {
        intent.getStringExtra(GAME_ID)
            ?: throw IllegalArgumentException("Intent Game ID must not be null")
    }

    val seasonYear: String by lazy {
        intent.getStringExtra(GAME_SEASON_YEAR)
            ?: throw IllegalArgumentException("Intent Season Year must not be null")
    }

    private val viewModel by activityViewModelLazy {
        Injector.get()
            .gameDetailsViewModelFactory()
            .create(gameId, seasonYear)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        setSupportActionBar(gamedetails_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show up button in toolbar

        error_view.setOnReloadButtonClickListener {
            viewModel.onReloadButtonClick()
        }

        viewModel.getToastEvents().observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        // Observe state of scores header
        viewModel.getScoreState().observe(this, Observer { score ->
            detail_score_header.setScore(score)
        })

        // Observe state of box score
        viewModel.getDetailsState().observe(this, Observer { detailsState ->
            when (detailsState) {
                is GameDetailsViewState.Success -> showGameDetails(detailsState.details)
                is GameDetailsViewState.Loading -> showLoading()
                is GameDetailsViewState.Failure -> showError(detailsState.message)
            }
        })
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    /**
     * Show game details view
     */
    private fun showGameDetails(details: GameDetails) {
        page_content.show(details_view)
        details_view.setGameDetails(details)
    }

    /**
     * Show progress spinner
     */
    private fun showLoading() {
        page_content.show(loading_view)
    }

    /**
     * Show error message
     */
    private fun showError(message: String) {
        page_content.show(error_view)
        error_view.setError(message)
    }
}
