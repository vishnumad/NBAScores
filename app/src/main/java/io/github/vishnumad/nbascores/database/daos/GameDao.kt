package io.github.vishnumad.nbascores.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.vishnumad.nbascores.database.entities.DbGame
import io.github.vishnumad.nbascores.database.entities.GameReadModel
import io.reactivex.Observable

@Dao
interface GameDao {

    @Query("""
        UPDATE DbGame
        SET
            home_score = :homeScore,
            away_score = :awayScore,
            label = :label
        WHERE gameId = :id
    """)
    fun updateScore(id: String, label: String, homeScore: String, awayScore: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGames(games: List<DbGame>)

    @Query("""
        DELETE FROM DbGame WHERE startDate = :date AND gameId NOT IN(:ids)
    """)
    fun clearUnneededGames(date: String, ids: List<String>)

    @Query("""
        SELECT
            game.*,
            ht.city homeCity,
            ht.nickname homeName,
            vt.city awayCity,
            vt.nickname awayName,
            hc.primaryColor homeColor,
            vc.primaryColor awayColor
        FROM DbGame game
        LEFT JOIN DbTeam ht ON game.home_teamID = ht.teamID
        LEFT JOIN DbTeam vt ON game.away_teamID = vt.teamID
        LEFT JOIN DbTeamConfig hc ON game.home_teamID = hc.teamID
        LEFT JOIN DbTeamConfig vc ON game.away_teamID = vc.teamID
        WHERE startDate = :date
            AND ht.city IS NOT NULL
            AND ht.nickname IS NOT NULL
            AND vt.city IS NOT NULL
            AND vt.nickname IS NOT NULL
            AND hc.primaryColor IS NOT NULL
            AND vc.primaryColor IS NOT NULL
    """)
    fun getGames(date: String): Observable<List<GameReadModel>>

    @Query("""
        SELECT
            game.*,
            ht.city homeCity,
            ht.nickname homeName,
            vt.city awayCity,
            vt.nickname awayName,
            hc.primaryColor homeColor,
            vc.primaryColor awayColor
        FROM DbGame game
        LEFT JOIN DbTeam ht ON game.home_teamID = ht.teamID
        LEFT JOIN DbTeam vt ON game.away_teamID = vt.teamID
        LEFT JOIN DbTeamConfig hc ON game.home_teamID = hc.teamID
        LEFT JOIN DbTeamConfig vc ON game.away_teamID = vc.teamID
        WHERE game.gameId = :id
            AND ht.city IS NOT NULL
            AND ht.nickname IS NOT NULL
            AND vt.city IS NOT NULL
            AND vt.nickname IS NOT NULL
            AND hc.primaryColor IS NOT NULL
            AND vc.primaryColor IS NOT NULL
    """)
    fun getGame(id: String): Observable<GameReadModel>
}