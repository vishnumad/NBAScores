package io.github.vishnumad.nbascores.remote.entities

import com.squareup.moshi.Json

data class LastPlayRaw(
        @field:Json(name = "evt") val event: Int,
        @field:Json(name = "de") val description: String,
        @field:Json(name = "cl") val clock: String,
        val locX: Int,
        val locY: Int,
        val opt1: Int,
        val opt2: Int,
        val mtype: Int,
        val etype: Int,
        val opid: String,
        val tid: Long,
        val pid: Long,
        val epid: String,
        val oftid: Long
)
