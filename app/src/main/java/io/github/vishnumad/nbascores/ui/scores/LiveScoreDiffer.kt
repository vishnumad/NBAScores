package io.github.vishnumad.nbascores.ui.scores

import io.github.vishnumad.nbascores.data.models.LiveScore
import io.github.vishnumad.nbascores.utils.DiffCallback

class LiveScoreDiffer(old: List<LiveScore>, new: List<LiveScore>) : DiffCallback<LiveScore>(old, new) {

    enum class Payload { LABEL, HOME_SCORE, AWAY_SCORE }

    override fun areItemsSame(oldItem: LiveScore, newItem: LiveScore): Boolean {
        return oldItem.gameId == newItem.gameId
    }

    override fun areContentsSame(oldItem: LiveScore, newItem: LiveScore): Boolean {
        return oldItem.status == newItem.status &&
                oldItem.mainLabel == newItem.mainLabel &&
                oldItem.homeScore == newItem.homeScore &&
                oldItem.awayScore == newItem.awayScore
    }

    override fun getChangePayload(oldItem: LiveScore, newItem: LiveScore): Any? {
        val changes = mutableListOf<Payload>()

        if (oldItem.mainLabel != newItem.mainLabel) changes.add(Payload.LABEL)
        if (oldItem.homeScore != newItem.homeScore) changes.add(Payload.HOME_SCORE)
        if (oldItem.awayScore != newItem.awayScore) changes.add(Payload.AWAY_SCORE)

        return changes
    }
}