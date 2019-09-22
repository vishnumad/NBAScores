package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

class BroadcasterRaw(
        @field:Json(name = "shortName") val shortName: String,
        @field:Json(name = "longName") val longName: String
)