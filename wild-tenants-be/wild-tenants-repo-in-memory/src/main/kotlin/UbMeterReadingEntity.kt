package ru.bugrimov.wt.repo.inmemory

import java.math.BigDecimal

data class UbMeterReadingEntity(
    var name: String? = null,
    var indicatedValue: BigDecimal? = null,
    var volumeForPeriod: BigDecimal? = null,
    var accruedSum: BigDecimal? = null,
    var paidAmount: BigDecimal? = null,
)
