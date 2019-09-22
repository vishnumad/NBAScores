package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

class BroadcastRaw(
        @field:Json(name = "broadcasters") val broadcasters: BroadcastersRaw
)