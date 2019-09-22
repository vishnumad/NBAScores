package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.LiveScore
import kotlinx.android.synthetic.main.detail_score_header_layout.view.*
import kotlinx.android.synthetic.main.schedule_list_item.view.away_team_icon
import kotlinx.android.synthetic.main.schedule_list_item.view.home_team_icon
import kotlinx.android.synthetic.main.score_list_item.view.*
import kotlinx.android.synthetic.main.score_list_item.view.away_team_score
import kotlinx.android.synthetic.main.score_list_item.view.home_team_score

class DetailScoreHeaderView : ConstraintLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.detail_score_header_layout, this, true)
    }

    fun setScore(score: LiveScore) {
        home_team_icon.setTeam(
            teamAbbrev = score.homeAbbrev,
            bgColor = Color.parseColor(score.homeColor)
        )
        away_team_icon.setTeam(
            teamAbbrev = score.awayAbbrev,
            bgColor = Color.parseColor(score.awayColor)
        )

        home_team_score.text = score.homeScore
        away_team_score.text = score.awayScore

        label.text = score.mainLabel
    }
}
