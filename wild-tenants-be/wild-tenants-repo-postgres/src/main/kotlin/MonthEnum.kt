package ru.bugrimov.wt.backend.repo.postgresql

import kotlinx.datetime.Month
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
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
            Month.JANUARY -> PgJanuary
            Month.FEBRUARY -> PgFebruary
            Month.MARCH -> PgMarch
            Month.APRIL -> PgApril
            Month.MAY -> PgMay
            Month.JUNE -> PgJune
            Month.JULY -> PgJuly
            Month.AUGUST -> PgAugust
            Month.SEPTEMBER -> PgSeptember
            Month.OCTOBER -> PgOctober
            Month.NOVEMBER -> PgNovember
            else -> PgDecember
        }
    }
)

sealed class PgValue(eValue: String) : PGobject() {
    init {
        type = UB_MONTH_TYPE
        value = eValue
    }
}

object PgJanuary: PgValue("january") {
    private fun readResolve(): Any = PgJanuary
}
object PgFebruary: PgValue("february") {
    private fun readResolve(): Any = PgFebruary
}
object PgMarch: PgValue("march") {
    private fun readResolve(): Any = PgMarch
}
object PgApril: PgValue("april") {
    private fun readResolve(): Any = PgApril
}
object PgMay: PgValue("may") {
    private fun readResolve(): Any = PgMay
}
object PgJune: PgValue("june") {
    private fun readResolve(): Any = PgJune
}
object PgJuly: PgValue("july") {
    private fun readResolve(): Any = PgJuly
}
object PgAugust: PgValue("august") {
    private fun readResolve(): Any = PgAugust
}
object PgSeptember: PgValue("september") {
    private fun readResolve(): Any = PgSeptember
}
object PgOctober: PgValue("october") {
    private fun readResolve(): Any = PgOctober
}
object PgNovember: PgValue("november") {
    private fun readResolve(): Any = PgNovember
}
object PgDecember: PgValue("december") {
    private fun readResolve(): Any = PgDecember
}