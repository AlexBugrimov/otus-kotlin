package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.repository.*
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoUbUpdateTest {

    abstract val repo: IRepoUb
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    private val updateIdNotFound = WtUbId("repo-update-not-found")
    private val lockBad = WtUbLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = WtUbLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        WtUb(
            id = updateSucc.id,
            ownerId = WtUserId("owner-123"),
            ubMeterReadings = listOf(
                UbMeterReading(name = MeterReadingName.COLD_WATER),
                UbMeterReading(name = MeterReadingName.HOT_WATER),
                UbMeterReading(name = MeterReadingName.ELECTRICITY),
            ),
            ubPeriod = UbPeriod(month = Month.JULY, year = 2020),
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = WtUb(
        id = updateIdNotFound,
        ownerId = WtUserId("owner-123"),
        ubMeterReadings = emptyList(),
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        WtUb(
            id = updateConc.id,
            ownerId = WtUserId("owner-123"),
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateUb(DbUbRequest(reqUpdateSucc))
        assertIs<DbUbResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.ubMeterReadings, result.data.ubMeterReadings)
        assertEquals(reqUpdateSucc.ubPeriod, result.data.ubPeriod)
        assertEquals(lockNew, result.data.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateUb(DbUbRequest(reqUpdateNotFound))
        assertIs<DbUbResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateUb(DbUbRequest(reqUpdateConc))
        assertIs<DbUbResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitUbs("update") {
        override val initObjects: List<WtUb> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
