package ru.bugrimov.wt.biz.validation

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.number
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.stubs.WtUbStub
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ValidationCreateTest: BaseBizValidationTest() {

    private val stub = WtUbStub.get()

    override val command: WtCommand = WtCommand.CREATE

    @Test
    fun `should be correct`() = runTest{
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
        )
        processor.exec(context)
        assertEquals(0, context.errors.size)
        assertNotEquals(WtState.FAILING, context.state)
        assertEquals(3, context.ubValidated.ubMeterReadings.size)
        assertTrue(context.ubValidated.ubPeriod.year <= LocalDate.now().year &&
                context.ubValidated.ubPeriod.month.number <= LocalDate.now().month.number)
    }

    @Test
    fun `should be invalid when meter readings are empty`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = stub.id,
                ubMeterReadings = emptyList(),
                ubPeriod = UbPeriod(Month.MARCH, 2024),
                lock = WtUbLock("123-234-abc-ABC"),
            ),
        )
        processor.exec(context)
        assertEquals(1, context.errors.size)
        assertEquals(WtState.FAILING, context.state)
    }

    @Test
    fun `should be invalid when meter readings values are negative`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = stub.id,
                ubMeterReadings = listOf(
                    UbMeterReading(MeterReadingName.ELECTRICITY, BigDecimal(200)),
                    UbMeterReading(MeterReadingName.HOT_WATER, BigDecimal(-100)),
                    UbMeterReading(MeterReadingName.COLD_WATER, BigDecimal(150))
                ),
                ubPeriod = UbPeriod(Month.MARCH, 2024),
                lock = WtUbLock("123-234-abc-ABC"),
            ),
        )
        processor.exec(context)
        assertEquals(1, context.errors.size)
        assertEquals(WtState.FAILING, context.state)
        assertEquals(0, context.ubValidated.ubMeterReadings.size)
    }

    @Test
    fun `should be invalid when period is bad`() = runTest {
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
            )
        )
        processor.exec(context)
        assertEquals(1, context.errors.size)
        assertEquals(WtState.FAILING, context.state)
    }
}