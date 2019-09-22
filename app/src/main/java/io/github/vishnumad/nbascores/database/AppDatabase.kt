package io.github.vishnumad.nbascores.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.vishnumad.nbascores.database.daos.GameDao
import io.github.vishnumad.nbascores.database.daos.GameDetailsDao
import io.github.vishnumad.nbascores.database.daos.StandingsDao
import io.github.vishnumad.nbascores.database.daos.TeamInfoDao
import io.github.vishnumad.nbascores.database.entities.*

@Database(
    version = 1,
    entities = [
        DbGame::class,
        DbTeam::class,
        DbTeamConfig::class,
        DbStanding::class,
        DbGameDetails::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun teamInfoDao(): TeamInfoDao
    abstract fun standingsDao(): StandingsDao
    abstract fun gameDetailsDao(): GameDetailsDao
}