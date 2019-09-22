package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.GameDetails
import io.github.vishnumad.nbascores.data.models.GameDetails.*
import io.github.vishnumad.nbascores.utils.hide
import io.github.vishnumad.nbascores.utils.show
import kotlinx.android.synthetic.main.details_view_layout.view.*

class GameDetailsView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.details_view_layout, this, true)
    }

    fun setGameDetails(details: GameDetails) {
        when (details) {
            is PreGameDetails -> {
                // Show only game location card
                game_location.show()
                last_play.hide()
                home_box_score.hide()
                away_box_score.hide()

                game_location.setGameLocation(details.arenaName, details.city, details.state)
            }
            is OngoingGameDetails -> {
                // Show last play card and box scores
                game_location.hide()
                last_play.show()
                home_box_score.show()
                away_box_score.show()

                last_play.setLastPlay(details.playClock, details.playDescription)

                home_box_score.setTeamName(details.homeTeamName)
                away_box_score.setTeamName(details.awayTeamName)
                home_box_score.setBoxScore(details.homePlayerStats, details.homeTeamStats)
                away_box_score.setBoxScore(details.awayPlayerStats, details.awayTeamStats)
            }
            is PostGameDetails -> {
                // Show only box scores
                game_location.hide()
                last_play.hide()
                home_box_score.show()
                away_box_score.show()

                home_box_score.setTeamName(details.homeTeamName)
                away_box_score.setTeamName(details.awayTeamName)
                home_box_score.setBoxScore(details.homePlayerStats, details.homeTeamStats)
                away_box_score.setBoxScore(details.awayPlayerStats, details.awayTeamStats)
            }
        }
    }
}