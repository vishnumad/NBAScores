package io.github.vishnumad.nbascores.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbTeamConfig(
        @PrimaryKey val teamID: Long,
        val tricode: String,
        val primaryColor: String
)