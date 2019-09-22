package io.github.vishnumad.nbascores.utils

import io.github.vishnumad.nbascores.di.LOCAL_ZONE
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

class DateTimeHelper @Inject constructor(
        @Named(LOCAL_ZONE) private val localZone: ZoneId
) {

    private val shortTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    private val humanDateFormatter = DateTimeFormatter.ofPattern("MMM d, u")

    private val etZoneId = ZoneId.of("America/New_York")

    fun convertUTCtoLocal(utcTime: String): String {
        val localZonedTime = ZonedDateTime.parse(utcTime)
            .withZoneSameInstant(localZone)
        return shortTimeFormatter.format(localZonedTime)
    }

    fun convertETtoLocal(etTime: String): String {
        val dateTime = LocalDateTime.parse(etTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val zonedTime = ZonedDateTime.of(dateTime, etZoneId)
            .withZoneSameInstant(localZone)
        return shortTimeFormatter.format(zonedTime)
    }

    fun humanReadableDate(date: LocalDate): String {
        return humanDateFormatter.format(date)
    }
}