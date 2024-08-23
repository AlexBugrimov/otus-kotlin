package ru.bugrimov.wt.mappers

import kotlinx.datetime.Month
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.stubs.WtStubs
import java.math.BigDecimal

fun WtContext.fromTransport(request: IRequest) = when (request) {
    is CreateRequest -> fromTransport(request)
    is ReadRequest -> fromTransport(request)
    is UpdateRequest -> fromTransport(request)
    is DeleteRequest -> fromTransport(request)
    is SearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

fun WtContext.fromTransport(createRequest: CreateRequest) {
    command = WtCommand.CREATE
    request = createRequest.ub?.toInternal() ?: WtUb()
    workMode = createRequest.debug.toWorkMode()
    stubCase = createRequest.debug.toStubCase()
}

fun WtContext.fromTransport(readRequest: ReadRequest) {
    command = WtCommand.READ
    request = readRequest.ub?.toInternal() ?: WtUb()
    workMode = readRequest.debug.toWorkMode()
    stubCase = readRequest.debug.toStubCase()
}

fun WtContext.fromTransport(updateRequest: UpdateRequest) {
    command = WtCommand.UPDATE
    request = updateRequest.ub?.toInternal() ?: WtUb()
    workMode = updateRequest.debug.toWorkMode()
    stubCase = updateRequest.debug.toStubCase()
}

fun WtContext.fromTransport(deleteRequest: DeleteRequest) {
    command = WtCommand.DELETE
    request = deleteRequest.ub?.toInternal() ?: WtUb()
    workMode = deleteRequest.debug.toWorkMode()
    stubCase = deleteRequest.debug.toStubCase()
}

fun WtContext.fromTransport(searchRequest: SearchRequest) {
    command = WtCommand.SEARCH
    filterRequest = searchRequest.ubFilter.toInternal()
    workMode = searchRequest.debug.toWorkMode()
    stubCase = searchRequest.debug.toStubCase()
}

private fun CreateObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(
    ubMeterReadings = this.meterReadings?.asUbMeterReadings() ?: emptyList(),
    ubPeriod = this.period?.asUbPeriod() ?: UbPeriod()
)

private fun ReadObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(id = WtUbId(id = id!!))

private fun UpdateObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(
    id = WtUbId(id!!),
    lock = this.lock?.let { WtUbLock(it) } ?: WtUbLock.NONE,
    ubMeterReadings = this.meterReadings?.asUbMeterReadings() ?: emptyList(),
    ubPeriod = this.period?.asUbPeriod() ?: UbPeriod()
)

private fun DeleteObject?.toInternal(): WtUb = if (this == null) WtUb() else WtUb(
    id = WtUbId(id!!),
    lock = this.lock?.let { WtUbLock(it) } ?: WtUbLock.NONE,
)

private fun SearchFilter?.toInternal(): WtUbFilter = if (this == null) WtUbFilter() else WtUbFilter(
    searchString = this.searchString ?: "",
    ownerId = this.ownerId?.let { WtUserId(it) } ?: WtUserId.NONE
)

private fun Period.asUbPeriod(): UbPeriod = UbPeriod(this.month.ubMonth() ?: java.time.Month.JANUARY, this.year ?: 1900)

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
            indicatedValue = it.value ?: BigDecimal.ZERO,
            volumeForPeriod = it.volumeForPeriod ?: BigDecimal.ZERO,
            accruedSum = it.accruedSum ?: BigDecimal.ZERO,
            paidAmount = it.paidAmount ?: BigDecimal.ZERO
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
    RequestDebugStubs.BAD_TITLE -> WtStubs.BAD_METER_READING
    RequestDebugStubs.BAD_DESCRIPTION -> WtStubs.BAD_PERIOD
    RequestDebugStubs.BAD_VISIBILITY -> WtStubs.BAD_VISIBILITY
    RequestDebugStubs.CANNOT_DELETE -> WtStubs.CANNOT_DELETE
    RequestDebugStubs.BAD_SEARCH_STRING -> WtStubs.BAD_SEARCH_STRING
    null -> WtStubs.NONE
}