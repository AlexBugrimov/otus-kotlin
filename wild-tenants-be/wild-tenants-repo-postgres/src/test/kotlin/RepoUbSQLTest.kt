import ru.bugrimov.wt.backend.repo.postgresql.RepoUbSql
import ru.bugrimov.wt.backend.repo.postgresql.SqlTestCompanion
import ru.bugrimov.wt.common.repository.IRepoUb
import ru.otus.otuskotlin.marketplace.backend.repo.tests.*
import ru.otus.otuskotlin.marketplace.repo.common.IRepoUbInitialize
import ru.otus.otuskotlin.marketplace.repo.common.UbRepoInitialized
import kotlin.test.AfterTest

private fun IRepoUb.clear() {
    val pgRepo = (this as UbRepoInitialized).repo as RepoUbSql
    pgRepo.clear()
}

class RepoSQLCreateTest : RepoUbCreateTest() {
    override val repo: IRepoUbInitialize = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { uuidNew.asString() },
    )

    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoSQLReadTest : RepoUbReadTest() {
    override val repo: IRepoUb = SqlTestCompanion.repoUnderTestContainer(initObjects)

    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoSQLUpdateTest : RepoUbUpdateTest() {
    override val repo: IRepoUb = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )

    @AfterTest
    fun tearDown() {
        repo.clear()
    }
}

class RepoSQLDeleteTest : RepoUbDeleteTest() {
    override val repo: IRepoUb = SqlTestCompanion.repoUnderTestContainer(initObjects)

    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoSQLSearchTest : RepoUbSearchTest() {
    override val repo: IRepoUb = SqlTestCompanion.repoUnderTestContainer(initObjects)

    @AfterTest
    fun tearDown() = repo.clear()
}
