package io.github.vishnumad.nbascores.ui.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.TeamStatline
import io.github.vishnumad.nbascores.remote.entities.PlayerStatline
import io.github.vishnumad.nbascores.ui.common.ContentCardView

class BoxScoreView : ContentCardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val teamNameView: TextView
    private val listView: ListView

    private val adapter: ArrayAdapter<PlayerStatline>

    private val teamStatsView: TeamStatlineView

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.boxscore_layout, this, true)

        teamNameView = findViewById(R.id.team_name)
        listView = findViewById(R.id.boxscore_list_view)

        // Header
        val header = PlayerStatlineHeaderView(context)
        listView.addHeaderView(header)

        teamStatsView = TeamStatlineView(context)
        listView.addFooterView(teamStatsView)

        adapter = BoxScoreAdapter(context)
        listView.adapter = adapter
    }

    fun setTeamName(name: String) {
        teamNameView.text = name
    }

    fun setBoxScore(playerStats: List<PlayerStatline>, teamStats: TeamStatline) {
        adapter.clear()
        adapter.addAll(playerStats)
        teamStatsView.setStats(teamStats)
        adapter.notifyDataSetChanged()
    }

    private inner class BoxScoreAdapter(context: Context) : ArrayAdapter<PlayerStatline>(context, 0) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView as? PlayerStatlineView
                    ?: PlayerStatlineView(context)

            val item = getItem(position) ?: throw Exception("No item at position $position in adapter")

            view.bind(item)
            return view
        }
    }
}