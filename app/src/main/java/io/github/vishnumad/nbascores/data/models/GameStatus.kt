package io.github.vishnumad.nbascores.data.models

enum class GameStatus {
    PRE, ONGOING, FINAL;

    companion object {
        fun fromInt(status: Int): GameStatus {
            return when (status) {
                1 -> PRE
                2 -> ONGOING
                3 -> FINAL
                else -> throw IllegalArgumentException("Invalid status type: $status")
            }
        }
    }
}
