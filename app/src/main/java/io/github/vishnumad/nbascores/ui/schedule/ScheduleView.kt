package io.github.vishnumad.nbascores.ui.schedule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.ScheduledGame
import kotlinx.android.synthetic.main.include_list_layout.view.*

class ScheduleView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val adapter: ScheduleAdapter
    private var listener: Listener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.include_list_layout, this, true)

        swipe_refresh.setOnRefreshListener {
            listener?.onSwipeRefresh()
        }

        adapter = ScheduleAdapter { game ->
            listener?.onScheduledGameClick(game.gameId, game.seasonYear)
        }
        recycler_view.adapter = adapter
    }

    fun updateSchedule(schedule: List<ScheduledGame>) {
        if (swipe_refresh.isRefreshing)
            swipe_refresh.isRefreshing = false

        adapter.updateSchedule(schedule)
    }

    fun clearSchedule() {
        adapter.clearSchedule()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onSwipeRefresh()
        fun onScheduledGameClick(gameId: String, seasonYear: String)
    }
}