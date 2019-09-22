package io.github.vishnumad.nbascores

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.vishnumad.nbascores.di.AppComponent
import io.github.vishnumad.nbascores.di.DaggerAppComponent

class NBAScoresApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        // Initialize Dagger component
        appComponent = DaggerAppComponent
            .factory()
            .create(this)

        // Initialize ThreeTenBP
        AndroidThreeTen.init(this)
    }

    companion object {
        private var INSTANCE: NBAScoresApp? = null

        @JvmStatic
        fun get(): NBAScoresApp = INSTANCE!!
    }
}