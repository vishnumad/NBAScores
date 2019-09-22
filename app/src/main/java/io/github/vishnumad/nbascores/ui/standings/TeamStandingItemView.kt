package io.github.vishnumad.nbascores.ui.standings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.TeamStanding

class TeamStandingItemView : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private val seedView: TextView
    private val nameView: TextView
    private val recordView: TextView
    private val winPctView: TextView
    private val gamesBackView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.standings_row_item, this, true)

        seedView = findViewById(R.id.seed)
        nameView = findViewById(R.id.team_name)
        recordView = findViewById(R.id.win_loss)
        winPctView = findViewById(R.id.win_pct)
        gamesBackView = findViewById(R.id.games_back)
    }

    fun bind(standing: TeamStanding) {
        seedView.text = standing.seed

        // Show playoff status if it exists
        nameView.text = when {
            standing.playoffsStatus.isEmpty() -> standing.name
            else -> "${standing.playoffsStatus} - ${standing.name}"
        }

        recordView.text = standing.record
        winPctView.text = standing.winPct
        gamesBackView.text = standing.gamesBack
    }
}
