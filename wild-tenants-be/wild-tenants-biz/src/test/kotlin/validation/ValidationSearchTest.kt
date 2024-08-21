package ru.bugrimov.wt.biz.validation

import kotlinx.coroutines.test.runTest
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.models.WtState.FAILING
import ru.bugrimov.wt.stubs.WtUbStub
import java.math.BigDecimal
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValidationSearchTest: BaseBizValidationTest() {

    private val stub = WtUbStub.get()

    override val command: WtCommand = WtCommand.SEARCH

    @Test
    fun `should be correct`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = stub.id,
                ubMeterReadings = listOf(
                    UbMeterReading(MeterReadingName.ELECTRICITY, BigDecimal(200)),
                    UbMeterReading(MeterReadingName.HOT_WATER, BigDecimal(100)),
                    UbMeterReading(MeterReadingName.COLD_WATER, BigDecimal(150))
                ),
                ubPeriod = UbPeriod(Month.MARCH, 2024),
                lock = WtUbLock("123-234-abc-ABC"),
            ),
            filterRequest = WtUbFilter(period = UbPeriod(Month.JULY, 2024)),
        )
        processor.exec(context)
        assertEquals(0, context.errors.size)
        assertNotEquals(FAILING, context.state)
    }

    @Test
    fun `should be correct when filter is empty`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = stub.id,
                ubMeterReadings = listOf(
                    UbMeterReading(MeterReadingName.ELECTRICITY, BigDecimal(200)),
                    UbMeterReading(MeterReadingName.HOT_WATER, BigDecimal(100)),
                    UbMeterReading(MeterReadingName.COLD_WATER, BigDecimal(150))
                ),
                ubPeriod = UbPeriod(Month.MARCH, 2024),
                lock = WtUbLock("123-234-abc-ABC"),
            ),
            filterRequest = WtUbFilter(),
        )
        processor.exec(context)
        assertEquals(0, context.errors.size)
        assertNotEquals(FAILING, context.state)
    }

    @Test
    fun `should return error when when period in filter is bad`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = stub.id,
                ubMeterReadings = listOf(
                    UbMeterReading(MeterReadingName.ELECTRICITY, BigDecimal(200)),
                    UbMeterReading(MeterReadingName.HOT_WATER, BigDecimal(100)),
                    UbMeterReading(MeterReadingName.COLD_WATER, BigDecimal(150))
                ),
                ubPeriod = UbPeriod(Month.MARCH, 2030),
                lock = WtUbLock("123-234-abc-ABC"),

            ),
            filterRequest = WtUbFilter(period = UbPeriod(Month.MARCH, 2050)),
        )
        processor.exec(context)
        assertEquals(1, context.errors.size)
        assertEquals(FAILING, context.state)
        assertEquals("period", context.errors[0].field)
        assertEquals("Validation error for field period: period must be current or in the past", context.errors[0].message)
    }
}