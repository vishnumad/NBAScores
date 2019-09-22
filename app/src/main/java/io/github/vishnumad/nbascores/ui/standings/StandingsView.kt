package io.github.vishnumad.nbascores.ui.standings

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import io.github.vishnumad.nbascores.data.models.TeamStanding

class StandingsView : NestedScrollView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private val easternStandings = StandingsCardView(context)
    private val westernStandings = StandingsCardView(context)

    init {
        easternStandings.setTitle("Eastern Conference")
        westernStandings.setTitle("Western Conference")

        val scrollViewChild = LinearLayout(context)
        scrollViewChild.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        scrollViewChild.orientation = LinearLayout.VERTICAL

        scrollViewChild.addView(easternStandings)
        scrollViewChild.addView(westernStandings)

        addView(scrollViewChild)
    }

    fun setStandings(east: List<TeamStanding>, west: List<TeamStanding>) {
        easternStandings.setStandings(east)
        westernStandings.setStandings(west)
    }
}

