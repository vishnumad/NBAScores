package io.github.vishnumad.nbascores.ui.scores

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.vishnumad.nbascores.data.models.LiveScore

class ScoresAdapter(val clickListener: (LiveScore) -> Unit) :
    RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder>() {

    private val liveScores = ArrayList<LiveScore>()

    fun updateScores(scores: List<LiveScore>) {
        liveScores.clear()
        liveScores.addAll(scores)
    }

    override fun getItemCount(): Int = liveScores.size

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        holder.item.bind(liveScores[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: ScoresViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.item.bind(liveScores[position], payloads[0] as List<LiveScoreDiffer.Payload>)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
        val item = LiveScoreItemView(parent.context)
        return ScoresViewHolder(item) { position ->
            clickListener(liveScores[position])
        }
    }

    class ScoresViewHolder(val item: LiveScoreItemView, listener: (Int) -> Unit) :
        RecyclerView.ViewHolder(item) {
        init {
            item.rootView.setOnClickListener { listener(adapterPosition) }
        }
    }
}