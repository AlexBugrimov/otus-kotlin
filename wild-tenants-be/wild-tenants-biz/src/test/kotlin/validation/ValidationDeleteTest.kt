package ru.bugrimov.wt.biz.validation

import kotlinx.coroutines.test.runTest
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.models.WtState.FAILING
import ru.bugrimov.wt.stubs.WtUbStub
import java.math.BigDecimal
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ValidationDeleteTest() : BaseBizValidationTest() {

    private val stub = WtUbStub.get()

    override val command: WtCommand = WtCommand.DELETE

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
        )
        processor.exec(context)
        assertEquals(0, context.errors.size)
        assertNotEquals(FAILING, context.state)
        assertEquals(3, context.ubValidated.ubMeterReadings.size)
    }

    @Test
    fun `should be invalid when id is empty`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = WtUbId(""),
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
        assertEquals(1, context.errors.size)
        assertEquals(FAILING, context.state)
        assertEquals("id", context.errors[0].field)
    }

    @Test
    fun `should be invalid when id contains incorrect format`() = runTest {
        val invalidId = "--**--"
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = WtUbId(invalidId),
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
        assertEquals(1, context.errors.size)
        assertEquals(FAILING, context.state)
        assertEquals("id", context.errors[0].field)
        assertEquals("Validation error for field id: value $invalidId must contain only letters and numbers", context.errors[0].message)
    }

    @Test
    fun `should return error when lock is empty`() = runTest {
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
                lock = WtUbLock(""),
            )
        )
        processor.exec(context)
        assertEquals(1, context.errors.size)
        assertEquals(FAILING, context.state)
        assertEquals("lock", context.errors[0].field)
        assertEquals("Validation error for field lock: field must not be empty", context.errors[0].message)
    }

    @Test
    fun `should return error when lock has incorrect format`() = runTest {
        val invalidLock = "$12"
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
                lock = WtUbLock(invalidLock),
            )
        )
        processor.exec(context)
        assertEquals(1, context.errors.size)
        assertEquals(FAILING, context.state)
        assertEquals("lock", context.errors[0].field)
        assertEquals("Validation error for field lock: value $invalidLock must contain only", context.errors[0].message)
    }

    @Test
    fun `should return two errors when id and lock are incorrect`() = runTest {
        val context = WtContext(
            command = command,
            state = WtState.NONE,
            workMode = WtWorkMode.TEST,
            request = WtUb(
                id = WtUbId(""),
                ubMeterReadings = listOf(
                    UbMeterReading(MeterReadingName.ELECTRICITY, BigDecimal(200)),
                    UbMeterReading(MeterReadingName.HOT_WATER, BigDecimal(100)),
                    UbMeterReading(MeterReadingName.COLD_WATER, BigDecimal(150))
                ),
                ubPeriod = UbPeriod(Month.MARCH, 2024),
                lock = WtUbLock(""),
            )
        )
        processor.exec(context)
        assertEquals(2, context.errors.size)
        assertEquals(FAILING, context.state)
        assertContains(context.errors.map { it.field }, "lock")
        assertContains(context.errors.map { it.field }, "id")
    }
}