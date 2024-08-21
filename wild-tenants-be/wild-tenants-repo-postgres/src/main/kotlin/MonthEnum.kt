package ru.bugrimov.wt.backend.repo.postgresql

import kotlinx.datetime.Month
import org.jetbrains.exposed.sql.Table
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.UB_MONTH_TYPE

fun Table.monthEnum(columnName: String) = customEnumeration(
    name = columnName,
    sql = UB_MONTH_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            "january" -> Month.JANUARY
            "february" -> Month.FEBRUARY
            "march" -> Month.MARCH
            "april" -> Month.APRIL
            "may" -> Month.MAY
            "june" -> Month.JUNE
            "july" -> Month.JULY
            "august" -> Month.AUGUST
            "september" -> Month.SEPTEMBER
            "october" -> Month.OCTOBER
            "november" -> Month.NOVEMBER
            else -> Month.DECEMBER
        }
    },
    toDb = { value ->
        when (value) {
            Month.JANUARY -> "january"
            Month.FEBRUARY -> "february"
            Month.MARCH -> "march"
            Month.APRIL -> "april"
            Month.MAY -> "may"
            Month.JUNE -> "june"
            Month.JULY -> "july"
            Month.AUGUST -> "august"
            Month.SEPTEMBER -> "september"
            Month.OCTOBER -> "october"
            Month.NOVEMBER -> "november"
            else -> "december"
        }
    }
)