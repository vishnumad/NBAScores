package io.github.vishnumad.nbascores

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
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

        ViewPump
            .init(
                ViewPump.builder()
                    .addInterceptor(
                        CalligraphyInterceptor(
                            CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Roboto_Condensed_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()
                        )
                    )
                    .build()
            )
    }

    companion object {
        private var INSTANCE: NBAScoresApp? = null

        @JvmStatic
        fun get(): NBAScoresApp = INSTANCE!!
    }
}