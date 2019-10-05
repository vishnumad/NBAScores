package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import io.github.vishnumad.nbascores.R
import kotlinx.android.synthetic.main.player_statline_layout.view.*

class PlayerStatlineHeaderView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private val headerColor = ContextCompat.getColor(context, R.color.main_text)

    private val boldTypeface =
        Typeface.createFromAsset(context.assets, context.getString(R.string.font_bold))

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.player_statline_layout, this, true)

        boldify(position, "")
        boldify(name, "NAME")
        boldify(minutes, "MIN")
        boldify(field_goals, "FG")
        boldify(three_pointers, "3PT")
        boldify(free_throws, "FT")
        boldify(offensive_rebounds, "OREB")
        boldify(rebounds, "REB")
        boldify(assists, "AST")
        boldify(steals, "STL")
        boldify(blocks, "BLK")
        boldify(turnovers, "TO")
        boldify(fouls, "PF")
        boldify(plus_minus, "+/-")
        boldify(points, "PTS")
    }

    private fun boldify(view: TextView, text: String) {
        with(view) {
            typeface = boldTypeface
            setTextColor(headerColor)
            this.text = text
        }
    }
}