package ru.bugrimov.wt.repo.inmemory

import ru.bugrimov.wt.common.models.*
import java.math.BigDecimal.ZERO
import java.time.Month

data class UbEntity(
    val id: String? = null,
    val ownerId: String? = null,
    val lock: String? = null,
    val meterReadings: List<UbMeterReadingEntity> = mutableListOf(),
    val monthPeriod: Month,
    val yearPeriod: Int,
) {

    constructor(model: WtUb) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        meterReadings = model.ubMeterReadings.map { it.toEntity() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        yearPeriod = model.ubPeriod.year,
        monthPeriod = model.ubPeriod.month,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = WtUb(
        id = id?.let { WtUbId(it) } ?: WtUbId.NONE,
        ownerId = ownerId?.let { WtUserId(it) } ?: WtUserId.NONE,
        lock = lock?.let { WtUbLock(it) } ?: WtUbLock.NONE,
        ubMeterReadings = meterReadings.map { it.toModel() },
        ubPeriod = UbPeriod(month = monthPeriod, year = yearPeriod)
    )
}

private fun UbMeterReading.toEntity(): UbMeterReadingEntity {
    return UbMeterReadingEntity(
        name = this.name.name,
        indicatedValue,
        volumeForPeriod,
        accruedSum,
        paidAmount
    )
}

private fun UbMeterReadingEntity.toModel(): UbMeterReading {
    return UbMeterReading(
        name = name?.let { MeterReadingName.valueOf(it) } ?: MeterReadingName.UNKNOWN,
        indicatedValue = indicatedValue?.let { it } ?: ZERO,
        volumeForPeriod = volumeForPeriod?.let { it } ?: ZERO,
        accruedSum = accruedSum?.let { it } ?: ZERO,
        paidAmount = paidAmount?.let { it } ?: ZERO,
    )
}
