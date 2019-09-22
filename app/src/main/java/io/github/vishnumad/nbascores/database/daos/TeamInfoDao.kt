package io.github.vishnumad.nbascores.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.vishnumad.nbascores.database.entities.DbTeam
import io.github.vishnumad.nbascores.database.entities.DbTeamConfig

@Dao
interface TeamInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTeams(teams: List<DbTeam>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTeamConfigs(configs: List<DbTeamConfig>)

    @Query("""
        SELECT COUNT(*)
        FROM DbTeam
        WHERE seasonYear = :seasonYear
    """)
    fun teamInfoCount(seasonYear: String): Int
}