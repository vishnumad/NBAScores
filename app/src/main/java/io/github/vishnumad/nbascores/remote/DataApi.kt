package io.github.vishnumad.nbascores.remote

import com.serjltt.moshi.adapters.Wrapped
import io.github.vishnumad.nbascores.remote.entities.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DataApi {

    companion object {
        const val BASE_URL = "https://data.nba.net/"
    }

    @GET("prod/{season_year}/teams_config.json")
    @Wrapped(path = ["teams", "config"])
    fun fetchTeamsConfig(@Path("season_year") seasonYear: String): Single<List<TeamConfigRaw>>

    @GET("prod/v1/{season_year}/teams.json")
    @Wrapped(path = ["league", "standard"])
    fun fetchTeams(@Path("season_year") seasonYear: String): Single<List<TeamRaw>>

    @GET("prod/v2/{date}/scoreboard.json")
    fun fetchScoreboard(@Path("date") date: String): Single<ScoreboardRaw>

    @GET("prod/v1/current/standings_conference.json")
    @Wrapped(path = ["league", "standard"])
    fun fetchConferenceStandings(): Single<StandardStandingsRaw>

    @GET("v2015/json/mobile_teams/nba/{season_year}/scores/gamedetail/{game_id}_gamedetail.json")
    @Wrapped(path = ["g"])
    fun fetchGameDetails(@Path("season_year") seasonYear: String, @Path("game_id") gameId: String): Single<GameDetailsRaw>
}