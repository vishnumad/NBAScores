package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.remote.entities.PlayerStatline
import kotlinx.android.synthetic.main.player_statline_layout.view.*

class PlayerStatlineView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.player_statline_layout, this, true)
    }

    fun bind(statline: PlayerStatline) {
        position.text = statline.pos
        name.text = "${statline.fn.first()}. ${statline.ln}"

        if (statline.totsec == 0) {
            // DNP
            minutes.text = statline.status
        } else {
            minutes.text = statline.min.toString()
        }

        field_goals.text = "${statline.fgm} - ${statline.fga}"
        three_pointers.text = "${statline.tpm} - ${statline.tpa}"
        free_throws.text = "${statline.ftm} - ${statline.fta}"
        offensive_rebounds.text = statline.oreb.toString()
        rebounds.text = statline.reb.toString()
        assists.text = statline.ast.toString()
        steals.text = statline.stl.toString()
        blocks.text = statline.blk.toString()
        turnovers.text = statline.tov.toString()
        fouls.text = statline.pf.toString()
        plus_minus.text = if (statline.pm > 0) "+${statline.pm}" else statline.pm.toString()
        points.text = statline.pts.toString()

    }
}