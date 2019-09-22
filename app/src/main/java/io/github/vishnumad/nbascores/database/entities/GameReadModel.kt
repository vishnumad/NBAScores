package io.github.vishnumad.nbascores.database.entities

import androidx.room.Embedded

data class GameReadModel(
        @Embedded val game: DbGame,
        val homeCity: String,
        val homeName: String,
        val homeColor: String,
        val awayCity: String,
        val awayName: String,
        val awayColor: String
)