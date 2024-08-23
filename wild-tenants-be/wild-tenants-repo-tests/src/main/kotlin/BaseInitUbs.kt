package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.*
import java.math.BigDecimal
import java.time.Month

abstract class BaseInitUbs(private val op: String) : IInitObjects<WtUb> {

    open val lockOld: WtUbLock = WtUbLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: WtUbLock = WtUbLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: WtUserId = WtUserId("owner-123"),
        lock: WtUbLock = lockOld,
    ) = WtUb(
        id = WtUbId("ad-repo-$op-$suf"),
        ubMeterReadings = listOf(
            UbMeterReading(
                name = MeterReadingName.COLD_WATER,
                indicatedValue = BigDecimal(344),
                volumeForPeriod = BigDecimal(200)
            ),
            UbMeterReading(
                name = MeterReadingName.HOT_WATER,
                indicatedValue = BigDecimal(320),
                volumeForPeriod = BigDecimal(210)
            ),
            UbMeterReading(
                name = MeterReadingName.ELECTRICITY,
                indicatedValue = BigDecimal(350),
                volumeForPeriod = BigDecimal(100)
            ),
        ),
        ubPeriod = UbPeriod(month = Month.MAY, year = 2023),
        ownerId = ownerId,
        lock = lock,
    )
}
