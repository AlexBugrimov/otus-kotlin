package ru.bugrimov.wt.backend.repo.postgresql.tables

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.LOCK
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.MONTH
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.OWNER_ID
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.UB_ID
import ru.bugrimov.wt.backend.repo.postgresql.SqlFields.YEAR
import ru.bugrimov.wt.backend.repo.postgresql.monthEnum
import ru.bugrimov.wt.common.models.*

class UtilityBillTable(name: String = "utility_bill") : Table(name) {

    val ubId = text(UB_ID)
    val owner = text(OWNER_ID)
    private val lock = text(LOCK)
    val month = monthEnum(MONTH)
    val year = integer(YEAR)

    override val primaryKey = PrimaryKey(ubId)

    fun from(res: ResultRow): WtUb {
        val meterReadingTable = MeterReadingTable()
        return WtUb(
            id = WtUbId(res[ubId].toString()),
            ownerId = WtUserId(res[owner].toString()),
            ubPeriod = UbPeriod(res[month], res[year]),
            ubMeterReadings = res[ubId].toString().let { meterReadingTable.select(meterReadingTable.ubId eq it)
                .map { mr -> meterReadingTable.from(mr) } } ,
            lock = WtUbLock(res[lock]),
        )
    }

    fun to(it: UpdateBuilder<*>, ub: WtUb, randomUuid: () -> String) {
        it[ubId] = ub.id.takeIf { it != WtUbId.NONE }?.asString() ?: randomUuid()
        it[month] = ub.ubPeriod.month
        it[year] = ub.ubPeriod.year
        it[owner] = ub.ownerId.asString()
        it[lock] = ub.lock.takeIf { it != WtUbLock.NONE }?.asString() ?: randomUuid()
    }
}

