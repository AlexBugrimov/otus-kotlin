package ru.bugrimov.wt.backend.repo.tests

import kotlinx.coroutines.test.runTest
import ru.bugrimov.wt.common.models.UbPeriod
import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.repository.*
import ru.bugrimov.wt.stubs.WtUbStub
import ru.otus.otuskotlin.marketplace.backend.repo.tests.UbRepositoryMock
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class UbRepositoryMockTest {

    private val repo = UbRepositoryMock(
        invokeCreateUb = { DbUbResponseOk(WtUbStub.prepareResult { ubPeriod = UbPeriod(Month.MARCH, 2024) }) },
        invokeReadUb = { DbUbResponseOk(WtUbStub.prepareResult { ubPeriod = UbPeriod(Month.JULY, 2024) }) },
        invokeUpdateUb = { DbUbResponseOk(WtUbStub.prepareResult { ubPeriod = UbPeriod(Month.JUNE, 2024) }) },
        invokeDeleteUb = { DbUbResponseOk(WtUbStub.prepareResult { ubPeriod = UbPeriod(Month.AUGUST, 2024) }) },
        invokeSearchUb = {
            DbUbsResponseOk(listOf(WtUbStub.prepareResult {
                ubPeriod = UbPeriod(Month.JANUARY, 2024)
            }))
        },
    )

    @Test
    fun `should create`() = runTest {
        val result = repo.createUb(DbUbRequest(WtUb()))
        assertIs<DbUbResponseOk>(result)
        assertEquals(UbPeriod(Month.MARCH, 2024), result.data.ubPeriod)
    }

    @Test
    fun `should read`() = runTest {
        val result = repo.readUb(DbUbIdRequest(WtUb()))
        assertIs<DbUbResponseOk>(result)
        assertEquals(UbPeriod(Month.JULY, 2024), result.data.ubPeriod)
    }

    @Test
    fun `should update`() = runTest {
        val result = repo.updateUb(DbUbRequest(WtUb()))
        assertIs<DbUbResponseOk>(result)
        assertEquals(UbPeriod(Month.JUNE, 2024), result.data.ubPeriod)
    }

    @Test
    fun `should delete`() = runTest {
        val result = repo.deleteUb(DbUbIdRequest(WtUb()))
        assertIs<DbUbResponseOk>(result)
        assertEquals(UbPeriod(Month.AUGUST, 2024), result.data.ubPeriod)
    }

    @Test
    fun `should search`() = runTest {
        val result = repo.searchUb(DbUbFilterRequest())
        assertIs<DbUbsResponseOk>(result)
        assertEquals(UbPeriod(Month.JANUARY, 2024), result.data.first().ubPeriod)
    }

}
