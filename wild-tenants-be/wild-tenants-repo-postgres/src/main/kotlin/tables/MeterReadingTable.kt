package ru.bugrimov.wt.backend.repo.postgresql.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.ACCRUED_SUM
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.INDICATED_VALUE
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.MR_ID
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.NAME
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.PAID_AMOUNT
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.UB_ID
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.VOLUME_FOR_PERIOD
import ru.bugrimov.wt.backend.repo.postgresql.nameEnum
import ru.bugrimov.wt.common.models.UbMeterReading
import ru.bugrimov.wt.common.models.WtUbId

class MeterReadingTable(name: String = "meter_reading") : Table(name) {

    private val mrId = integer(MR_ID).autoIncrement()
    private val name = nameEnum(NAME)
    private val indicatedValue = decimal(INDICATED_VALUE, 6, 2)
    private val volumeForPeriod = decimal(VOLUME_FOR_PERIOD, 6, 2)
    private val accruedSum = decimal(ACCRUED_SUM, 6, 2)
    private val paidAmount = decimal(PAID_AMOUNT, 6, 2)
    val ubId = text(UB_ID).references(UtilityBillTable().ubId)

    override val primaryKey = PrimaryKey(mrId)

    fun from(res: ResultRow) = UbMeterReading(
        name = res[name],
        indicatedValue = res[indicatedValue],
        volumeForPeriod = res[volumeForPeriod],
        accruedSum = res[accruedSum],
        paidAmount = res[paidAmount],
    )

    fun to(it: UpdateBuilder<*>, meterReading: UbMeterReading, id: WtUbId) {
        it[name] = meterReading.name
        it[indicatedValue] = meterReading.indicatedValue
        it[volumeForPeriod] = meterReading.volumeForPeriod
        it[accruedSum] = meterReading.accruedSum
        it[paidAmount] = meterReading.paidAmount
        it[ubId] = id.asString()
    }
}

private operator fun String?.set(ubId: Column<String>, value: String?) {
    ubId.let { value }
}
