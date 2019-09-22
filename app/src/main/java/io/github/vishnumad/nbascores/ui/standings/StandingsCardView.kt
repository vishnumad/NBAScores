package io.github.vishnumad.nbascores.ui.standings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.TeamStanding
import io.github.vishnumad.nbascores.ui.common.ContentCardView

private const val NUM_TEAMS = 15

class StandingsCardView : ContentCardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val titleView: TextView
    private val listView: ListView

    private val arrayAdapter: ArrayAdapter<TeamStanding>

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.standings_container_layout, this, true)

        titleView = findViewById(R.id.container_title)
        listView = findViewById(R.id.standings_list)

        val header = View.inflate(context, R.layout.standings_header_row, null)
        listView.addHeaderView(header)

        /* Prevent the view from "jumping" once the data has been added.
            We know the size because there are only 15 teams in each conference. */
        header.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        listView.updateLayoutParams {
            height += (header.measuredHeight * NUM_TEAMS)
        }

        arrayAdapter = StandingsAdapter(context)
        listView.adapter = arrayAdapter

        listView.setFooterDividersEnabled(false)
    }

    fun setStandings(standings: List<TeamStanding>) {
        arrayAdapter.clear()
        arrayAdapter.addAll(standings)
        arrayAdapter.notifyDataSetChanged()
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    inner class StandingsAdapter(context: Context) : ArrayAdapter<TeamStanding>(context, 0) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView as? TeamStandingItemView
                    ?: TeamStandingItemView(parent.context)

            val item = getItem(position) ?: throw Exception("No item at position: $position in adapter")

            view.bind(item)
            return view
        }
    }
}


