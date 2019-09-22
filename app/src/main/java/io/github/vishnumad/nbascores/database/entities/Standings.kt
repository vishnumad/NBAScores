package io.github.vishnumad.nbascores.database.entities

data class Standings(
        val seasonYear: String,
        val east: List<DbStanding>,
        val west: List<DbStanding>
)