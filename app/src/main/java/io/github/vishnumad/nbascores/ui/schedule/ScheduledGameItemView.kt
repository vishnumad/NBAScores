package io.github.vishnumad.nbascores.ui.schedule

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.ScheduledGame
import io.github.vishnumad.nbascores.ui.common.ContentCardView
import kotlinx.android.synthetic.main.schedule_list_item.view.*

class ScheduledGameItemView : ContentCardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.schedule_list_item, this, true)
    }

    fun bind(game: ScheduledGame) {
        start_time.text = game.startTime
        game_location.text = game.arena

        if (game.broadcasters.isEmpty()) {
            broadcasters_icon.visibility = View.GONE
            broadcasters.visibility = View.GONE
        } else {
            broadcasters_icon.visibility = View.VISIBLE
            broadcasters.visibility = View.VISIBLE
            broadcasters.text = game.broadcasters
        }

        home_team_name.text =
            context.getString(R.string.live_score_team_name, game.homeCity, game.homeName)
        away_team_name.text =
            context.getString(R.string.live_score_team_name, game.awayCity, game.awayName)

        home_team_icon.setTeam(game.homeAbbrev, Color.parseColor(game.homeColor))
        away_team_icon.setTeam(game.awayAbbrev, Color.parseColor(game.awayColor))
    }

}