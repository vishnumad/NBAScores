package io.github.vishnumad.nbascores.di

import io.github.vishnumad.nbascores.NBAScoresApp

class Injector private constructor() {
    companion object {
        fun get() = NBAScoresApp.get().appComponent
    }
}