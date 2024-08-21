package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.repository.DbUbRequest
import ru.bugrimov.wt.common.repository.DbUbResponseOk
import ru.otus.otuskotlin.marketplace.repo.common.IRepoUbInitialize
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals


abstract class RepoUbCreateTest {
    abstract val repo: IRepoUbInitialize
    protected open val uuidNew = WtUbId("10000000-0000-0000-0000-000000000001")

    private val createObj = WtUb(
        ubMeterReadings = listOf(
            UbMeterReading(name = MeterReadingName.COLD_WATER),
            UbMeterReading(name = MeterReadingName.HOT_WATER),
            UbMeterReading(name = MeterReadingName.ELECTRICITY),
        ),
        ubPeriod = UbPeriod(month = Month.JULY, year = 2020),
        ownerId = WtUserId("owner-123")
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createUb(DbUbRequest(createObj))
        val expected = createObj
        assertIs<DbUbResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.ubMeterReadings.size, result.data.ubMeterReadings.size)
        assertEquals(expected.ubPeriod.year, result.data.ubPeriod.year)
        assertNotEquals(WtUbId.NONE, result.data.id)
    }

    companion object : BaseInitUbs("create") {
        override val initObjects: List<WtUb> = emptyList()
    }
}
