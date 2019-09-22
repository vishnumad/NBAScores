package io.github.vishnumad.nbascores.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.vishnumad.nbascores.database.entities.DbGameDetails
import io.reactivex.Observable

@Dao
interface GameDetailsDao {

    @Query("""
        SELECT *
        FROM DbGameDetails
        WHERE gameId = :gameId
    """)
    fun getGameDetails(gameId: String): Observable<DbGameDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGameDetails(details: DbGameDetails)
}