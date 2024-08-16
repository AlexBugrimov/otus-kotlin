package ru.bugrimov.wt.api.log1.mapper

import kotlinx.datetime.Clock
import ru.bugrimov.wild_tenants.be.api.log.models.*
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*

fun WtContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "wind-tenants",
    ub = toUbLog(),
    errors = errors.map { it.toLog() },
)

private fun WtContext.toUbLog(): LogModel? {
    val ubNone = WtUb()
    return LogModel(
        requestId = requestId.takeIf { it != WtRequestId.NONE }?.asString(),
        request = request.takeIf { it != ubNone }?.toLog(),
        response = response.takeIf { it != ubNone }?.toLog(),
        responses = ubsResponse.takeIf { it.isNotEmpty() }?.filter { it != ubNone }?.map { it.toLog() },
        requestFilter = filterRequest.takeIf { it != WtUbFilter() }?.toLog(),
    ).takeIf { it != LogModel() }
}

private fun WtUbFilter.toLog() = UbFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != WtUserId.NONE }?.asString()
)

private fun WtError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun WtUb.toLog() = UbLog(
    id = id.takeIf { it != WtUbId.NONE }?.asString(),
    meterReadings = ubMeterReadings.takeIf { it.isNotEmpty() }?.map { it.toReading() },
    period = ubPeriod.toPeriod(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)

private fun UbPeriod?.toPeriod() = if (this != null) "${this.month}/${this.year}" else ""

private fun UbMeterReading.toReading(): MeterReading {
    return MeterReading(
        name = MeterReading.Name.valueOf(this.name.name),
        value = indicatedValue,
        volumeForPeriod,
        accruedSum,
        paidAmount
    )
}
