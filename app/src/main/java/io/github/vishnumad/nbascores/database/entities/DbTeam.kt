package io.github.vishnumad.nbascores.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbTeam(
        @PrimaryKey val teamID: Long,
        val seasonYear: String,
        val city: String,
        val fullName: String,
        val tricode: String,
        val nickname: String,
        val urlName: String,
        val confName: String,
        val divName: String
)