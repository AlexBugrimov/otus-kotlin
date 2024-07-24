package ru.bugrimov.windtenants.common.models

import kotlinx.datetime.Month

typealias Year = Int

data class UbPeriod(
    val month: Month? = Month.JANUARY,
    val year: Year = 1900
)
