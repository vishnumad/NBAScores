package io.github.vishnumad.nbascores.remote.entities

data class TeamRaw(
        val isNBAFranchise: Boolean,
        val isAllStar: Boolean,
        val city: String,
        val altCityName: String,
        val fullName: String,
        val tricode: String,
        val teamId: Long,
        val nickname: String,
        val urlName: String,
        val confName: String,
        val divName: String
)