package ru.bugrimov.wt.mappers

import kotlinx.datetime.Month
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.windtenants.common.WtContext
import ru.bugrimov.windtenants.common.models.*
import ru.bugrimov.windtenants.common.stubs.WtStubs

fun CreateRequest.asContext(): WtContext = WtContext(
    command = WtCommand.CREATE,
    request = ub.toInternal(),
    workMode = debug.toWorkMode(),
    stubCase = debug.toStubCase()
)

fun ReadRequest.asContext(): WtContext = WtContext(
    command = WtCommand.READ,
    request = ub.toInternal(),
    workMode = debug.toWorkMode(),
    stubCase = debug.toStubCase()
)

fun UpdateRequest.asContext(): WtContext = WtContext(
    command = WtCommand.UPDATE,
    request = ub.toInternal(),
    workMode = debug.toWorkMode(),
    stubCase = debug.toStubCase()
)

fun DeleteRequest.asContext(): WtContext = WtContext(
    command = WtCommand.DELETE,
    request = ub.toInternal(),
    workMode = debug.toWorkMode(),
    stubCase = debug.toStubCase()
)


fun SearchRequest.asContext(): WtContext = WtContext(
    command = WtCommand.SEARCH,
    filterRequest = ubFilter.toInternal(),
    workMode = debug.toWorkMode(),
    stubCase = debug.toStubCase()
)

private fun CreateObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(
    ubMeterReadings = this.meterReadings?.asUbMeterReadings() ?: emptyList(),
    ubPeriod = this.period?.asUbPeriod() ?: UbPeriod()
)

private fun ReadObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(id = WtUbId(id))

private fun UpdateObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(
    id = WtUbId(id),
    lock = this.lock?.let { WtUbLock(it) } ?: WtUbLock.NONE,
    ubMeterReadings = this.meterReadings?.asUbMeterReadings() ?: emptyList(),
    ubPeriod = this.period?.asUbPeriod() ?: UbPeriod()
)

private fun DeleteObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(
    id = WtUbId(id),
    lock = this.lock?.let { WtUbLock(it) } ?: WtUbLock.NONE,
)

private fun SearchFilter?.toInternal(): WtUbFilter = if (this == null) WtUbFilter() else WtUbFilter(
    searchString = this.searchString ?: "",
    ownerId = this.ownerId?.let { WtUserId(it) } ?: WtUserId.NONE
)

private fun Period.asUbPeriod(): UbPeriod = UbPeriod(this.month.ubMonth(), this.year ?: 1900)

private fun Period.Month?.ubMonth(): Month? = when (this) {
    Period.Month.JAN -> Month.JANUARY
    Period.Month.FEB -> Month.FEBRUARY
    Period.Month.MAR -> Month.MARCH
    Period.Month.APR -> Month.APRIL
    Period.Month.MAY -> Month.MAY
    Period.Month.JUN -> Month.JUNE
    Period.Month.JUL -> Month.JULY
    Period.Month.AUG -> Month.AUGUST
    Period.Month.SEP -> Month.SEPTEMBER
    Period.Month.OCT -> Month.OCTOBER
    Period.Month.NOV -> Month.NOVEMBER
    Period.Month.DEC -> Month.DECEMBER
    else -> null
}

private fun List<MeterReading>.asUbMeterReadings(): List<UbMeterReading> {
    return this.map {
        UbMeterReading(
            name = it.name.asMeterReadingName(),
            indicatedValue = it.value,
            volumeForPeriod = it.volumeForPeriod,
            accruedSum = it.accruedSum,
            paidAmount = it.paidAmount
        )
    }
}

private fun MeterReading.Name?.asMeterReadingName(): MeterReadingName = when (this) {
    MeterReading.Name.ELECTRICITY -> MeterReadingName.ELECTRICITY
    MeterReading.Name.COLD_WATER -> MeterReadingName.COLD_WATER
    MeterReading.Name.HOT_WATER -> MeterReadingName.HOT_WATER
    null -> MeterReadingName.UNKNOWN
}

private fun UbDebug?.toWorkMode(): WtWorkMode = when (this?.mode) {
    RequestDebugMode.PROD -> WtWorkMode.PROD
    RequestDebugMode.TEST -> WtWorkMode.TEST
    RequestDebugMode.STUB -> WtWorkMode.STUB
    null -> WtWorkMode.PROD
}

private fun UbDebug?.toStubCase(): WtStubs = when (this?.stub) {
    RequestDebugStubs.SUCCESS -> WtStubs.SUCCESS
    RequestDebugStubs.NOT_FOUND -> WtStubs.NOT_FOUND
    RequestDebugStubs.BAD_ID -> WtStubs.BAD_ID
    RequestDebugStubs.BAD_TITLE -> WtStubs.BAD_TITLE
    RequestDebugStubs.BAD_DESCRIPTION -> WtStubs.BAD_DESCRIPTION
    RequestDebugStubs.BAD_VISIBILITY -> WtStubs.BAD_VISIBILITY
    RequestDebugStubs.CANNOT_DELETE -> WtStubs.CANNOT_DELETE
    RequestDebugStubs.BAD_SEARCH_STRING -> WtStubs.BAD_SEARCH_STRING
    null -> WtStubs.NONE
}