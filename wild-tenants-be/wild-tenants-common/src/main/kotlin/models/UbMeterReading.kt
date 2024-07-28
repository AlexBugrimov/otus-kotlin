package ru.bugrimov.windtenants.common.models

import java.math.BigDecimal

data class UbMeterReading(
    var name: MeterReadingName = MeterReadingName.UNKNOWN,
    var indicatedValue: BigDecimal? = BigDecimal.ZERO,
    var volumeForPeriod: BigDecimal? = BigDecimal.ZERO,
    var accruedSum: BigDecimal? = BigDecimal.ZERO,
    var paidAmount: BigDecimal? = BigDecimal.ZERO,
)