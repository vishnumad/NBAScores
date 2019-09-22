package io.github.vishnumad.nbascores.database.entities

data class GamePeriod(
    val current: Int,
    val type: Int,
    val maxRegular: Int,
    val isHalftime: Boolean,
    val isEndOfPeriod: Boolean
)