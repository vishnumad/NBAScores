package io.github.vishnumad.nbascores.data

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class SeasonYearTest {

    @Test
    fun season_year_should_be_previous_year_when_before_July() {
        val date = LocalDate.of(2019, 5, 10) // May 10, 2019
        val seasonYear = SeasonYear(date)

        assertEquals(seasonYear.string, "${date.year - 1}")
    }

    @Test
    fun season_year_should_be_current_year_when_July_or_after() {
        val date = LocalDate.of(2019, 8, 15) // Sep 15, 2019
        val seasonYear = SeasonYear(date)

        assertEquals(seasonYear.string, "${date.year}")
    }
}