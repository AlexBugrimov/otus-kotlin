package ru.bugrimov.wt.backend.repo.postgresql

import org.jetbrains.exposed.sql.Table
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.MR_NAME_TYPE
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
            MeterReadingName.HOT_WATER -> "hot_water"
            MeterReadingName.COLD_WATER -> "cold_water"
            MeterReadingName.ELECTRICITY -> "electricity"
            else -> "unknown"
        }
    }
)