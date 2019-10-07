package io.github.vishnumad.nbascores.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.vishnumad.nbascores.database.AppDatabase
import io.github.vishnumad.nbascores.database.daos.GameDao
import io.github.vishnumad.nbascores.database.daos.GameDetailsDao
import io.github.vishnumad.nbascores.database.daos.StandingsDao
import io.github.vishnumad.nbascores.database.daos.TeamInfoDao
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun appDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "scores_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun gameDao(db: AppDatabase): GameDao = db.gameDao()

    @Provides
    @Singleton
    fun teamInfoDao(db: AppDatabase): TeamInfoDao = db.teamInfoDao()

    @Provides
    @Singleton
    fun standingsDao(db: AppDatabase): StandingsDao = db.standingsDao()

    @Provides
    @Singleton
    fun gameDetailsDao(db: AppDatabase): GameDetailsDao = db.gameDetailsDao()
}