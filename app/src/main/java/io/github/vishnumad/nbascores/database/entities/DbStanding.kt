package io.github.vishnumad.nbascores.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbStanding(
        @PrimaryKey val teamID: Long,
        val conference: String,
        val sortKey: Int,
        val clinchedPlayoffs: String,
        val record: String,
        val homeRecord: String,
        val awayRecord: String,
        val winPct: String,
        val gamesBack: String,
        val last10: String,
        val streak: String
)
