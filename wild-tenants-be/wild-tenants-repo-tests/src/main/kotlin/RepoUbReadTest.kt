package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.repository.DbUbIdRequest
import ru.bugrimov.wt.common.repository.DbUbResponseErr
import ru.bugrimov.wt.common.repository.DbUbResponseOk
import ru.bugrimov.wt.common.repository.IRepoUb
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoUbReadTest {

    abstract val repo: IRepoUb
    protected open val obj = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readUb(DbUbIdRequest(obj.id))

        assertIs<DbUbResponseOk>(result)
        assertEquals(obj, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readUb(DbUbIdRequest(notFoundId))

        assertIs<DbUbResponseErr>(result)
        val error: WtError? = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitUbs("read") {
        override val initObjects: List<WtUb> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = WtUbId("ub-repo-read-notFound")

    }
}
