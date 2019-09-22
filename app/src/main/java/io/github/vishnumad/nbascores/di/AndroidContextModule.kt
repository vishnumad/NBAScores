package io.github.vishnumad.nbascores.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AndroidContextModule(private val applicationContext: Context) {

    @Provides
    fun appContext(): Context = applicationContext
}