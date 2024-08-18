package ru.bugrimov.wt.common.models

import kotlinx.datetime.Month
import java.time.LocalDate
import java.util.*

typealias Year = Int

data class UbPeriod(
    val month: Month = Month.JANUARY,
    val year: Year = 1900
) {

    fun asDate() = LocalDate.of(year, month, 28)

    companion object {
        val CURRENT: UbPeriod =
            UbPeriod(Month(Calendar.getInstance().get(Calendar.MONTH)), Calendar.getInstance().get(Calendar.YEAR))
    }
}
