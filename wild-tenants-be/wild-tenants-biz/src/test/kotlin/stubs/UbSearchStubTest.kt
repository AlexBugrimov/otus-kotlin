package ru.bugrimov.wt.biz.stubs

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import ru.bugrimov.wt.biz.WtProcessor
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.models.WtCommand.*
import ru.bugrimov.wt.common.models.WtState.NONE
import ru.bugrimov.wt.common.models.WtWorkMode.STUB
import ru.bugrimov.wt.common.stubs.WtStubs
import ru.bugrimov.wt.common.stubs.WtStubs.BAD_VISIBILITY
import ru.bugrimov.wt.common.stubs.WtStubs.DB_ERROR
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class UbSearchStubTest {

    private val processor = WtProcessor()
    private val filter = WtUbFilter(period = UbPeriod(month = Month.MARCH, year = 2017))

    @Test
    fun `should find ub by period`() = runTest {

        val context = WtContext(
            command = SEARCH,
            state = NONE,
            workMode = STUB,
            stubCase = WtStubs.SUCCESS,
            filterRequest = filter,
        )
        processor.exec(context)
        assertTrue(context.ubsResponse.size > 1)
        val first = context.ubsResponse.firstOrNull() ?: fail("Empty response list")
        assertEquals(first.ubPeriod, filter.period)
    }

    @Test
    fun `should return error when id is invalid`() = runTest {
        val context = WtContext(
            command = SEARCH,
            state = NONE,
            workMode = STUB,
            stubCase = WtStubs.BAD_ID,
            filterRequest = filter
        )

        processor.exec(context)

        assertEquals(WtUb(), context.response)
        assertEquals("id", context.errors.firstOrNull()?.field)
        assertEquals("validation", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when the database doesn't respond`() = runTest {
        val context = WtContext(
            command = SEARCH,
            state = NONE,
            workMode = STUB,
            stubCase = DB_ERROR,
            filterRequest = filter
        )
        processor.exec(context)

        Assertions.assertEquals(WtUb(), context.response)
        Assertions.assertEquals("internal", context.errors.firstOrNull()?.group)
    }

    @Test
    fun `should return error when case is not possible`() = runTest {
        val context = WtContext(
            command = SEARCH,
            state = NONE,
            workMode = STUB,
            stubCase = BAD_VISIBILITY,
            filterRequest = filter
        )

        processor.exec(context)

        Assertions.assertEquals(WtUb(), context.response)
        Assertions.assertEquals("stubs", context.errors.firstOrNull()?.field)
        Assertions.assertEquals("validation", context.errors.firstOrNull()?.group)
    }
}
