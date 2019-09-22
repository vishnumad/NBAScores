package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class RefereeRaw(
        @field:Json(name = "fn") val firstName: String,
        @field:Json(name = "ln") val lastName: String,
        @field:Json(name = "num") val number: String
)