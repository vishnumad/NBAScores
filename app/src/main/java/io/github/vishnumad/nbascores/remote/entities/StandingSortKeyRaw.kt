package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class StandingSortKeyRaw(@field:Json(name = "defaultOrder") val default: Int)