package io.github.vishnumad.nbascores.ui.scores

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.GameStatus
import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.ui.common.ContentCardView
import io.github.vishnumad.nbascores.ui.common.TeamIcon

class LiveScoreItemView : ContentCardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mainLabel: TextView
    private val secondaryLabel: TextView

    private val homeName: TextView
    private val homeScore: TextView
    private val homeIcon: TeamIcon

    private val awayName: TextView
    private val awayScore: TextView
    private val awayIcon: TeamIcon

    init {
        LayoutInflater.from(context).inflate(R.layout.score_list_item, this, true)

        mainLabel = findViewById(R.id.mainLabel)
        secondaryLabel = findViewById(R.id.secondaryLabel)

        homeName = findViewById(R.id.home_team_name)
        homeScore = findViewById(R.id.home_team_score)
        homeIcon = findViewById(R.id.home_team_icon)

        awayName = findViewById(R.id.away_team_name)
        awayScore = findViewById(R.id.away_team_score)
        awayIcon = findViewById(R.id.away_team_icon)
    }

    fun bind(score: LiveScore) {
        mainLabel.text = score.mainLabel
        secondaryLabel.text = score.secondaryLabel

        if (score.status == GameStatus.PRE) {
            homeScore.text = ""
            awayScore.text = ""
        } else {
            homeScore.text = score.homeScore
            awayScore.text = score.awayScore
        }

        homeName.text = context.getString(R.string.live_score_team_name, score.homeCity, score.homeName)
        homeIcon.setTeam(score.homeAbbrev, Color.parseColor(score.homeColor))

        awayName.text = context.getString(R.string.live_score_team_name, score.awayCity, score.awayName)
        awayIcon.setTeam(score.awayAbbrev, Color.parseColor(score.awayColor))
    }

    fun bind(score: LiveScore, payloads: List<LiveScoreDiffer.Payload>) {
        for (payload in payloads) {
            when (payload) {
                LiveScoreDiffer.Payload.LABEL -> mainLabel.text = score.mainLabel
                LiveScoreDiffer.Payload.HOME_SCORE -> homeScore.text = score.homeScore
                LiveScoreDiffer.Payload.AWAY_SCORE -> awayScore.text = score.awayScore
            }
        }
    }
}