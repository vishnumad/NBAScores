package io.github.vishnumad.nbascores.data

import dagger.Reusable
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

@Reusable
class SeasonYear @Inject constructor(@Named("current_date") date: LocalDate) {

    val string: String

    init {
        val formatter = DateTimeFormatter.ofPattern("YYYY")

        /* NBA summer league games start in July so we use the current year
         * when the month is after June. Otherwise we use the previous year
         */
        string = if (date.month >= Month.JULY) {
            date.format(formatter)
        } else {
            date.minusYears(1).format(formatter)
        }
    }
}