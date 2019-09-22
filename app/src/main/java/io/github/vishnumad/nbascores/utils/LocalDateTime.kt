package io.github.vishnumad.nbascores.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

fun LocalDateTime.basicISODate(): String {
    return DateTimeFormatter.BASIC_ISO_DATE.format(this)
}
