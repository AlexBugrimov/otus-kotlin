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

class ValidationReadTest: BaseBizValidationTest() {

    private val stub = WtUbStub.get()

    override val command: WtCommand = WtCommand.READ

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
        val invalidId = "****"
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
}