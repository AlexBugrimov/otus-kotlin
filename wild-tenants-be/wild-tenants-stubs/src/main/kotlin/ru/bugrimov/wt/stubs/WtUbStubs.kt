package ru.bugrimov.wt.stubs

import ru.bugrimov.wt.common.models.*
import java.math.BigDecimal
import java.time.Month

object WtUbStubs {

    val WT_UB: WtUb
        get() = WtUb(
            id = WtUbId("666"),
            ownerId = WtUserId("USER-123"),
            lock = WtUbLock("123-234-abc-ABC"),
            ubPeriod = UbPeriod(Month.JULY, 2004),
            ubMeterReadings = listOf(
                UbMeterReading(
                    name = MeterReadingName.HOT_WATER,
                    indicatedValue = BigDecimal(200),
                    volumeForPeriod = BigDecimal(400),
                )
            ),
            permissionsClient = mutableSetOf(
                WtUbPermissionClient.READ,
                WtUbPermissionClient.UPDATE,
                WtUbPermissionClient.DELETE,
            )
        )
    val WT_UB_SUPPLY = WT_UB.copy(ubPeriod = UbPeriod(Month.MAY, 2025))
}