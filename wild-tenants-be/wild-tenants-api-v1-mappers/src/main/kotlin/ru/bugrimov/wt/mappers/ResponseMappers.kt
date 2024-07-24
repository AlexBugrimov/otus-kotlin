package ru.bugrimov.wt.mappers

import kotlinx.datetime.Month
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.windtenants.common.WtContext
import ru.bugrimov.windtenants.common.models.*

fun WtContext.toResponse(): IResponse = when (val cmd = command) {
    WtCommand.CREATE -> toCreate()
    WtCommand.READ -> toRead()
    WtCommand.UPDATE -> toUpdate()
    WtCommand.DELETE -> toDelete()
    WtCommand.SEARCH -> toSearch()
    WtCommand.INIT -> toInit()
    WtCommand.FINISH -> object : IResponse {
        override val responseType: String? = null
        override val result: ResponseResult? = null
        override val errors: List<Error>? = null
    }
    WtCommand.NONE -> throw UnknownCommand(cmd)
}

private fun WtContext.toInit(): IResponse = InitResponse(
    result = state.toResult(),
    errors = errors.toErrors()
)

private fun WtContext.toSearch(): IResponse = SearchResponse(
    result = state.toResult(),
    errors = errors.toErrors(),
    ubs = ubsResponse.toTransportUbs()
)

private fun WtContext.toDelete(): IResponse = DeleteResponse(
    result = state.toResult(),
    errors = errors.toErrors(),
    ub = response.toTransportUb()
)

private fun WtContext.toUpdate(): IResponse = UpdateResponse(
    result = state.toResult(),
    errors = errors.toErrors(),
    ub = response.toTransportUb()
)

private fun WtContext.toRead(): IResponse = ReadResponse(
    result = state.toResult(),
    errors = errors.toErrors(),
    ub = response.toTransportUb()
)

fun WtContext.toCreate(): IResponse = CreateResponse(
    result = state.toResult(),
    errors = errors.toErrors(),
    ub = response.toTransportUb()
)

fun List<WtUb>.toTransportUbs(): List<ResponseObject>? = this
    .map { it.toTransportUb() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun WtUb.toTransportUb(): ResponseObject = ResponseObject(
    id = id.takeIf { it != WtUbId.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != WtUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportUb(),
    lock = lock.takeIf { it != WtUbLock.NONE }?.asString(),
    period = ubPeriod.toTransportUb(),
    meterReadings = ubMeterReadings.toTransportUb()
)

private fun List<UbMeterReading>.toTransportUb(): List<MeterReading> {
    return this.map {
        MeterReading(
            name = it.name.toTransportUb(),
            value = it.indicatedValue,
            volumeForPeriod = it.volumeForPeriod,
            accruedSum = it.accruedSum,
            paidAmount = it.paidAmount
        )
    }
}

private fun MeterReadingName.toTransportUb(): MeterReading.Name? = when (this) {
    MeterReadingName.ELECTRICITY -> MeterReading.Name.ELECTRICITY
    MeterReadingName.COLD_WATER -> MeterReading.Name.COLD_WATER
    MeterReadingName.HOT_WATER -> MeterReading.Name.HOT_WATER
    else -> null
}

private fun UbPeriod.toTransportUb(): Period {
    return Period(
        month = this.month?.toTransportUb(),
        year = this.year
    )
}

private fun Month.toTransportUb(): Period.Month? = when (this) {
    Month.JANUARY -> Period.Month.JAN
    Month.FEBRUARY  -> Period.Month.FEB
    Month.MARCH  -> Period.Month.MAR
    Month.APRIL  -> Period.Month.APR
    Month.MAY  -> Period.Month.MAY
    Month.JUNE  -> Period.Month.JUN
    Month.JULY  -> Period.Month.JUL
    Month.AUGUST  -> Period.Month.AUG
    Month.SEPTEMBER  -> Period.Month.SEP
    Month.OCTOBER  -> Period.Month.OCT
    Month.NOVEMBER  -> Period.Month.NOV
    Month.DECEMBER  -> Period.Month.DEC
    else -> null
}


private fun Set<WtUbPermissionClient>.toTransportUb(): Set<Permissions>? = this
    .map { it.toTransportUb() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun WtUbPermissionClient.toTransportUb() = when (this) {
    WtUbPermissionClient.READ -> Permissions.READ
    WtUbPermissionClient.UPDATE -> Permissions.UPDATE
    WtUbPermissionClient.MAKE_VISIBLE_OWNER -> Permissions.MAKE_VISIBLE_OWN
    WtUbPermissionClient.MAKE_VISIBLE_GROUP -> Permissions.MAKE_VISIBLE_GROUP
    WtUbPermissionClient.MAKE_VISIBLE_PUBLIC -> Permissions.MAKE_VISIBLE_PUBLIC
    WtUbPermissionClient.DELETE -> Permissions.DELETE
}

private fun List<WtError>.toErrors(): List<Error>? = this
    .map { it.toTransportWt() }
    .toList()
    .takeIf { it.isNotEmpty() }

internal fun WtError.toTransportWt() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

internal fun WtState.toResult(): ResponseResult? = when (this) {
    WtState.RUNNING, WtState.FINISHING -> ResponseResult.SUCCESS
    WtState.FAILING -> ResponseResult.ERROR
    WtState.NONE -> null
}

class UnknownCommand(cmd: WtCommand) : RuntimeException("Unknown command $cmd")
