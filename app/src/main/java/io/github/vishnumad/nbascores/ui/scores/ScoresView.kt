package io.github.vishnumad.nbascores.ui.scores

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.data.models.LiveScore

class ScoresView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private val swipeRefreshLayout: SwipeRefreshLayout
    private val recyclerView: RecyclerView

    private val adapter: ScoresAdapter

    private var listener: Listener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.include_list_layout, this, true)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh)
        recyclerView = findViewById(R.id.recycler_view)

        swipeRefreshLayout.setOnRefreshListener {
            listener?.onSwipeRefresh()
        }

        adapter = ScoresAdapter { score ->
            listener?.onScoreClick(score.gameId, score.seasonYear)
        }

        recyclerView.adapter = adapter
    }

    fun updateScores(scores: List<LiveScore>, diff: DiffUtil.DiffResult) {
        if (swipeRefreshLayout.isRefreshing)
            swipeRefreshLayout.isRefreshing = false

        adapter.updateScores(scores)
        diff.dispatchUpdatesTo(adapter)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onSwipeRefresh()
        fun onScoreClick(gameId: String, seasonYear: String)
    }

}