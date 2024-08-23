package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.repository.*
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoUbSearchTest {
    abstract val repo: IRepoUb

    protected open val initializedObjects: List<WtUb> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchUb(DbUbFilterRequest(ownerId = searchOwnerId))
        assertIs<DbUbsResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    @Test
    fun searchDealSide() = runRepoTest {
        val result = repo.searchUb(DbUbFilterRequest(periodFilter = UbPeriod(Month.MAY, 2024)))
        assertIs<DbUbsResponseOk>(result)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitUbs("search") {

        val searchOwnerId = WtUserId("owner-124")
        override val initObjects: List<WtUb> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", ownerId = searchOwnerId)
        )
    }
}
