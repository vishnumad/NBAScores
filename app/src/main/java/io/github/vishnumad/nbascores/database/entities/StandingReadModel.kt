package io.github.vishnumad.nbascores.database.entities

import androidx.room.Embedded

data class StandingReadModel(
    @Embedded val dbStanding: DbStanding,
    val fullName: String
)