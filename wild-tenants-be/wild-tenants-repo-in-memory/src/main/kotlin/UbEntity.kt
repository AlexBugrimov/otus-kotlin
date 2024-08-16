package ru.bugrimov.wt.repo.inmemory

data class UbEntity(
    val id: String? = null,
    val ownerId: String? = null,
    val lock: String? = null,
    val meterReadings: List<UbMeterReadingEntity> = mutableListOf(),
    val monthPeriod: String? = null,
    val yearPeriod: Int? = null,
)
