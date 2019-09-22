package io.github.vishnumad.nbascores.database;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import io.github.vishnumad.nbascores.database.entities.DbGame;
import io.github.vishnumad.nbascores.database.entities.GamePeriod;
import io.github.vishnumad.nbascores.database.entities.GameTeamScore;
import io.reactivex.functions.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class GameDaoTest2 {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private AppDatabase database;

    @Before
    public void initDatabase() {
        database = Room
                .inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        AppDatabase.class
                )
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDatabase() {
        database.close();
    }

    @Test
    public void insert_DbGame_and_get_DbGame_by_date() {
        DbGame game = getDbGame();

        ArrayList<DbGame> games = new ArrayList<>();
        games.add(game);

        database.gameDao().saveGames(games);

        database.gameDao().getGames(game.getStartDate())
                .test()
                .assertValue(new Predicate<List<DbGame>>() {
                    @Override
                    public boolean test(List<DbGame> dbGames) throws Exception {
                        return dbGames.size() == 1;
                    }
                });
    }

    private DbGame getDbGame() {
        return new DbGame(
                1234L,
                20190508,
                "2018",
                2,
                1,
                "4:00",
                false,
                new GamePeriod(0, 0, 4, false, false),
                new GameTeamScore(123, "AAA", "41", "41", "2", "2", "100"),
                new GameTeamScore(456, "ZZZ", "41", "41", "2", "2", "100")
        );
    }
}
