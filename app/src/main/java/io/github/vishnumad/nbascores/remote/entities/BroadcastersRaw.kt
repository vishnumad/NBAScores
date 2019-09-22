package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

class BroadcastersRaw(
        @field:Json(name = "national") val national: List<BroadcasterRaw>,
        @field:Json(name = "vTeam") val away: List<BroadcasterRaw>,
        @field:Json(name = "hTeam") val home: List<BroadcasterRaw>
)