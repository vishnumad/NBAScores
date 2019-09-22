package io.github.vishnumad.nbascores.data.models

data class ConferenceStandings(val east: List<TeamStanding>, val west: List<TeamStanding>) {

    fun isEmpty(): Boolean = east.isEmpty() || west.isEmpty()
}