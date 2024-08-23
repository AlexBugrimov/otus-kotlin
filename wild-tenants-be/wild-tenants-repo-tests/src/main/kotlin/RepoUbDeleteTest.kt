package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.repository.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoUbDeleteTest {

    abstract val repo: IRepoUb
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = WtUbId("repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteUb(DbUbIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbUbResponseOk>(result)
        assertEquals(deleteSucc.ubMeterReadings, result.data.ubMeterReadings)
        assertEquals(deleteSucc.ubPeriod, result.data.ubPeriod)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readUb(DbUbIdRequest(notFoundId, lock = lockOld))

        assertIs<DbUbResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteUb(DbUbIdRequest(deleteConc.id, lock = lockBad))

        assertIs<DbUbResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitUbs("delete") {
        override val initObjects: List<WtUb> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
