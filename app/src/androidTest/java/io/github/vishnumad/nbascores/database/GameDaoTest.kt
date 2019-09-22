package io.github.vishnumad.nbascores.database

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.vishnumad.nbascores.database.entities.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class GameDaoTest {

    @get:Rule
    var instantExecutor: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase

    @Before
    fun initDatabase() {
        appDatabase = Room
            .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext<Context>(),
                    AppDatabase::class.java
            )
            .allowMainThreadQueries() // All queries on main thread for testing
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        appDatabase.close()
    }


    @Test
    fun getGames_should_emit_empty_list_when_no_configs() {
        val game = getDbGame()
        appDatabase.gameDao().saveGames(listOf(game))

        appDatabase.gameDao().getGames(date = game.startDate)
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun getGames_should_emit_value_when_configs_exist() {
        appDatabase.teamInfoDao().saveTeamConfigs(listOf(
                DbTeamConfig(123, "AAA", "#123456"),
                DbTeamConfig(456, "ZZZ", "#567890")
        ))
        appDatabase.teamInfoDao().saveTeams(listOf(
                DbTeam(123, "City1", "City Rollers", "AAA", "Rollers", "url", "A", "A", "east"),
                DbTeam(456, "City2", "City Ballers", "ZZZ", "Ballers", "url", "Z", "Z", "west")
        ))

        val game = getDbGame()
        appDatabase.gameDao().saveGames(listOf(game))

        appDatabase.gameDao().getGames(date = game.startDate)
            .doOnNext { Log.d("Test", it.toString()) }
            .test()
            .await(5, TimeUnit.SECONDS)
    }

    private fun getDbGame(): DbGame {
        return DbGame(
                gameId = 1234,
                status = 1,
                period = GamePeriod(0, 0, 4, false, false),
                isStartTimeTBD = false,
                seasonStage = 4,
                seasonYear = "2018",
                startDate = "20190508",
                startTimeUTC = "4:00",
                awayTeam = GameTeamScore(123, "AAA", "41", "41", "2", "2", "100"),
                homeTeam = GameTeamScore(456, "ZZZ", "41", "41", "2", "2", "100"),
                arena = "Arena",
                arenaCity = "Arena City",
                broadcasters = "ABC, ESPN, FSN",
                gameClock = "6:28"
        )
    }

}