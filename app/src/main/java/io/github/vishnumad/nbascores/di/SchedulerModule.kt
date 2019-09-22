package io.github.vishnumad.nbascores.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

const val IO_THREAD = "io_scheduler"
const val MAIN_THREAD = "main_scheduler"
const val COMPUTATION_THREAD = "computation_scheduler"

@Module
object SchedulerModule {

    @Provides
    @Named(IO_THREAD)
    @JvmStatic
    fun ioScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named(MAIN_THREAD)
    @JvmStatic
    fun mainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Named(COMPUTATION_THREAD)
    @JvmStatic
    fun computationScheduler(): Scheduler {
        return Schedulers.computation()
    }
}