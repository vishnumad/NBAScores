package io.github.vishnumad.nbascores.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

class DateTimeHelperTest {

    @Test
    fun `convert UTC to Central Time`() {
        val centralZoneId = ZoneId.of("America/Chicago")
        val converter = DateTimeHelper(centralZoneId)

        val testTime = "2018-12-15T22:00:00.000Z" // 5:00 PM ET
        val expectedTime = "4:00 PM"

        val resultTime = converter.convertUTCtoLocal(testTime)

        assertEquals(expectedTime, resultTime)
    }


    @Test
    fun `convert UTC to Pacific Time`() {
        val centralZoneId = ZoneId.of("US/Pacific")
        val converter = DateTimeHelper(centralZoneId)

        val testTime = "2018-12-15T22:00:00.000Z" // 5:00 PM ET
        val expectedTime = "2:00 PM"

        val resultTime = converter.convertUTCtoLocal(testTime)

        assertEquals(expectedTime, resultTime)
    }

    @Test
    fun `human readable date`() {
        val converter = DateTimeHelper(ZoneId.of("US/Pacific"))

        val testDate = LocalDate.of(2019, 5, 4)
        val expected = "May 4, 2019"

        val result = converter.humanReadableDate(testDate)

        assertEquals(expected, result)
    }

    @Test
    fun  `convert ET to Central Time`() {
        val converter = DateTimeHelper(ZoneId.of("America/Chicago"))

        val testTime = "2019-01-07T19:30:00" // 7:30 PM ET
        val expectedTime = "6:30 PM"

        val resultTime = converter.convertETtoLocal(testTime)

        assertEquals(expectedTime, resultTime)
    }

    @Test
    fun  `convert ET to Pacific Time`() {
        val converter = DateTimeHelper(ZoneId.of("US/Pacific"))

        val testTime = "2019-01-07T19:30:00" // 7:30 PM ET
        val expectedTime = "4:30 PM"

        val resultTime = converter.convertETtoLocal(testTime)

        assertEquals(expectedTime, resultTime)
    }
}