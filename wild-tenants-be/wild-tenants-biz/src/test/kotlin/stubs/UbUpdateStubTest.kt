package ru.bugrimov.wt.biz.stubs

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import ru.bugrimov.wt.biz.WtProcessor
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.models.WtCommand.UPDATE
import ru.bugrimov.wt.common.models.WtState.NONE
import ru.bugrimov.wt.common.models.WtWorkMode.STUB
import ru.bugrimov.wt.common.stubs.WtStubs.*
import ru.bugrimov.wt.stubs.WtUbStub
import java.math.BigDecimal
import java.time.Month
import kotlin.test.Test

class UbUpdateStubTest {

    private val processor = WtProcessor()
    private val id = WtUbId("666")
    private val meterReading = listOf(
        UbMeterReading(name = MeterReadingName.HOT_WATER, indicatedValue = BigDecimal(123)),
        UbMeterReading(name = MeterReadingName.COLD_WATER, indicatedValue = BigDecimal(234)),
        UbMeterReading(name = MeterReadingName.ELECTRICITY, indicatedValue = BigDecimal(1243)),
    )
    private val period = UbPeriod(month = Month.MARCH, 2024)

    @Test
    fun `should update Ub`() = runTest {

        val context = WtContext(
            command = UPDATE,
            state = NONE,
            workMode = STUB,
            stubCase = SUCCESS,
            request = WtUb(
                id = id,
                ubMeterReadings = meterReading,
                ubPeriod = period,
            )
        )

        processor.exec(context)

        assertEquals(WtUbStub.get().id, context.response.id)
        assertEquals(meterReading, context.response.ubMeterReadings)
        assertEquals(period, context.response.ubPeriod)
    }

    @Test
    fun `should return error when meter reading is empty`() = runTest {
        val context = WtContext(
            command = UPDATE,
            state = NONE,
            workMode = STUB,
            stubCase = BAD_METER_READING,
            request = WtUb(
                id = id,
                ubMeterReadings = emptyList(),
                ubPeriod = period,
            )
        )

        processor.exec(context)

        assertEquals(WtUb(), context.response)
        assertEquals("meter-readings", context.errors.firstOrNull()?.field)
        assertEquals("validation", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when period is invalid`() = runTest {
        val context = WtContext(
            command = UPDATE,
            state = NONE,
            workMode = STUB,
            stubCase = BAD_PERIOD,
            request = WtUb(
                id = id,
                ubMeterReadings = meterReading,
                ubPeriod = UbPeriod(Month.MARCH, -100),
            )
        )

        processor.exec(context)

        assertEquals(WtUb(), context.response)
        assertEquals("period", context.errors.firstOrNull()?.field)
        assertEquals("validation", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when the database doesn't respond`() = runTest {
        val context = WtContext(
            command = UPDATE,
            state = NONE,
            workMode = STUB,
            stubCase = DB_ERROR,
            request = WtUb(id = id)
        )
        processor.exec(context)

        assertEquals(WtUb(), context.response)
        assertEquals("internal", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when case is not possible`() = runTest {
        val context = WtContext(
            command = UPDATE,
            state = NONE,
            workMode = STUB,
            stubCase = BAD_SEARCH_STRING,
            request = WtUb(
                id = id,
                ubMeterReadings = meterReading,
                ubPeriod = period
            )
        )

        processor.exec(context)

        assertEquals(WtUb(), context.response)
        assertEquals("stubs", context.errors.firstOrNull()?.field)
        assertEquals("validation", context.errors.firstOrNull()?.group)
    }
}
