package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

class WatchRaw(
    @field:Json(name = "broadcast") val broadcast: BroadcastRaw
)