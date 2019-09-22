package io.github.vishnumad.nbascores.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.vishnumad.nbascores.database.entities.DbStanding
import io.github.vishnumad.nbascores.database.entities.StandingReadModel
import io.reactivex.Observable

@Dao
interface StandingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStandings(standings: List<DbStanding>)

    @Query("""
        SELECT
            DbStanding.*,
            DbTeam.fullName
        FROM DbStanding
        LEFT JOIN DbTeam ON DbStanding.teamID = DbTeam.teamID
        WHERE DbStanding.conference = :conference
            AND DbTeam.fullName IS NOT NULL
        ORDER BY DbStanding.sortKey
    """)
    fun getConferenceStandings(conference: String): Observable<List<StandingReadModel>>
}