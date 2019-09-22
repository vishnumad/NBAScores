package io.github.vishnumad.nbascores.data.models

import io.github.vishnumad.nbascores.remote.entities.PlayerStatline

sealed class GameDetails {

    data class PreGameDetails(
        val arenaName: String,
        val city: String,
        val state: String
    ) : GameDetails()

    data class OngoingGameDetails(
        val playClock: String,
        val playDescription: String,
        val homeTeamName: String,
        val awayTeamName: String,
        val homePlayerStats: List<PlayerStatline>,
        val awayPlayerStats: List<PlayerStatline>,
        val homeTeamStats: TeamStatline,
        val awayTeamStats: TeamStatline
    ) : GameDetails()

    data class PostGameDetails(
        val homeTeamName: String,
        val awayTeamName: String,
        val homePlayerStats: List<PlayerStatline>,
        val awayPlayerStats: List<PlayerStatline>,
        val homeTeamStats: TeamStatline,
        val awayTeamStats: TeamStatline
    ) : GameDetails()
}
