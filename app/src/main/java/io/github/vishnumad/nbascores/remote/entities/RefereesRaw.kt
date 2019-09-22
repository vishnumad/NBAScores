package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class RefereesRaw(@field:Json(name = "off") val refsList: List<RefereeRaw>)