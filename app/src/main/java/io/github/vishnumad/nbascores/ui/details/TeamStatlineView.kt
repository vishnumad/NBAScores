package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.TeamStatline
import kotlinx.android.synthetic.main.player_statline_layout.view.*

class TeamStatlineView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.player_statline_layout, this, true)

    }

    fun setStats(stats: TeamStatline) {
        field_goals.text = stats.fg
        three_pointers.text = stats.threePt
        free_throws.text = stats.ft
        offensive_rebounds.text = stats.oreb
        rebounds.text = stats.reb
        assists.text = stats.ast
        steals.text = stats.stl
        blocks.text = stats.blk
        turnovers.text = stats.to
        fouls.text = stats.pf
        points.text = stats.pts
    }
}