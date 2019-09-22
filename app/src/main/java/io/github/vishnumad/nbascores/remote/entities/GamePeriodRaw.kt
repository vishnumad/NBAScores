package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class GamePeriodRaw(
        @field:Json(name = "current") val current: Int,
        @field:Json(name = "type") val type: Int,
        @field:Json(name = "maxRegular") val maxRegular: Int,
        @field:Json(name = "isHalftime") val isHalftime: Boolean,
        @field:Json(name = "isEndOfPeriod") val isEndOfPeriod: Boolean
)