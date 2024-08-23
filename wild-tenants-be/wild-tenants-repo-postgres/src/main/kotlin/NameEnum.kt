package ru.bugrimov.wt.backend.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.MR_NAME_TYPE
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.UB_MONTH_TYPE
import ru.bugrimov.wt.common.models.MeterReadingName

fun Table.nameEnum(columnName: String) = customEnumeration(
    name = columnName,
    sql = MR_NAME_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            "hot_water" -> MeterReadingName.HOT_WATER
            "cold_water" -> MeterReadingName.COLD_WATER
            else -> MeterReadingName.ELECTRICITY
        }
    },
    toDb = { value ->
        when (value) {
            MeterReadingName.HOT_WATER ->  PgHotWater
            MeterReadingName.COLD_WATER -> PgColdWater
            MeterReadingName.ELECTRICITY -> PgElectricity
            else -> "unknown"
        }
    }
)

sealed class PgNameValue(eValue: String) : PGobject() {
    init {
        type = MR_NAME_TYPE
        value = eValue
    }
}

object PgHotWater: PgNameValue("hot_water") {
    private fun readResolve(): Any = PgHotWater
}

object PgColdWater: PgNameValue("cold_water") {
    private fun readResolve(): Any = PgColdWater
}
object PgElectricity: PgNameValue("electricity") {
    private fun readResolve(): Any = PgElectricity
}