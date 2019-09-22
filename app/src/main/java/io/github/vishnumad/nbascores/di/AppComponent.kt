package io.github.vishnumad.nbascores.di

import android.content.Context
import com.squareup.moshi.JsonAdapter
import dagger.BindsInstance
import dagger.Component
import io.github.vishnumad.nbascores.ui.details.GameDetailsViewModel
import io.github.vishnumad.nbascores.ui.schedule.ScheduleViewModel
import io.github.vishnumad.nbascores.ui.scores.ScoresViewModel
import io.github.vishnumad.nbascores.ui.standings.StandingsViewModel
import io.github.vishnumad.nbascores.remote.entities.PlayerStatline
import javax.inject.Singleton

@Component(modules = [AppModule::class, DbModule::class, SchedulerModule::class])
@Singleton
interface AppComponent {

    // ViewModels

    fun scoresViewModel(): ScoresViewModel
    fun scheduleViewModel(): ScheduleViewModel
    fun standingsViewModel(): StandingsViewModel

    // ViewModelFactory
    fun gameDetailsViewModelFactory(): GameDetailsViewModel.Factory

    fun playerStatlineJsonAdapter(): JsonAdapter<List<PlayerStatline>>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}