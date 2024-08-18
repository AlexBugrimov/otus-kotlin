package ru.bugrimov.wt.biz.stubs

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import ru.bugrimov.wt.biz.WtProcessor
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.UbPeriod
import ru.bugrimov.wt.common.models.WtCommand.READ
import ru.bugrimov.wt.common.models.WtState.NONE
import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.models.WtWorkMode.STUB
import ru.bugrimov.wt.common.stubs.WtStubs.*
import ru.bugrimov.wt.stubs.WtUbStub
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals

class UbReadStubTest {

    private val processor = WtProcessor()
    private val id = WtUbId("666")

    @Test
    fun `should read ub`() = runTest {
        val context = WtContext(
            command = READ,
            state = NONE,
            workMode = STUB,
            stubCase = SUCCESS,
            request = WtUb(
                id = id,
                ubPeriod = UbPeriod(Month.JULY, 2004)
            )
        )

        processor.exec(context)

        val stub = WtUbStub.get()
        assertEquals(stub.id, context.response.id)
        assertEquals(stub.ubMeterReadings, context.response.ubMeterReadings)
        assertEquals(stub.ubPeriod, context.response.ubPeriod)
    }

    @Test
    fun `should return error when id is bad`() = runTest {
        val context = WtContext(
            command = READ,
            state = NONE,
            workMode = STUB,
            stubCase = BAD_ID,
            request = WtUb()
        )

        processor.exec(context)

        assertEquals(WtUb(), context.response)
        assertEquals("id", context.errors.firstOrNull()?.field)
        assertEquals("validation", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when the database doesn't respond`() = runTest {
        val context = WtContext(
            command = READ,
            state = NONE,
            workMode = STUB,
            stubCase = DB_ERROR,
            request = WtUb(id = id)
        )

        processor.exec(context)

        Assertions.assertEquals(WtUb(), context.response)
        Assertions.assertEquals("internal", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when case is not possible`() = runTest {
        val context = WtContext(
            command = READ,
            state = NONE,
            workMode = STUB,
            stubCase = BAD_VISIBILITY,
            request = WtUb(id = id)
        )

        processor.exec(context)

        Assertions.assertEquals(WtUb(), context.response)
        Assertions.assertEquals("stubs", context.errors.firstOrNull()?.field)
        Assertions.assertEquals("validation", context.errors.firstOrNull()?.group)
    }
}
