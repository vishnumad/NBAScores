package io.github.vishnumad.nbascores.ui.schedule

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.vishnumad.nbascores.data.models.ScheduledGame

class ScheduleAdapter(val clickListener: (ScheduledGame) -> Unit) :
        RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private val schedule = mutableListOf<ScheduledGame>()

    fun updateSchedule(schedule: List<ScheduledGame>) {
        this.schedule.clear()
        this.schedule.addAll(schedule)
        notifyDataSetChanged()
    }

    fun clearSchedule() {
        this.schedule.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = schedule.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.item.bind(schedule[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val gameItem = ScheduledGameItemView(parent.context)
        return ScheduleViewHolder(gameItem) { position ->
            clickListener(schedule[position])
        }
    }

    class ScheduleViewHolder(val item: ScheduledGameItemView, listener: (Int) -> Unit) :
            RecyclerView.ViewHolder(item) {
        init {
            item.rootView.setOnClickListener { listener(adapterPosition) }
        }
    }
}