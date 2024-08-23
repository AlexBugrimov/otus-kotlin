package ru.bugrimov.wt.backend.repo.postgresql

import com.benasher44.uuid.uuid4
import ru.bugrimov.wt.common.models.WtUb
import ru.otus.otuskotlin.marketplace.repo.common.IRepoUbInitialize
import ru.otus.otuskotlin.marketplace.repo.common.UbRepoInitialized

object SqlTestCompanion {
    private const val HOST = "localhost"
    private const val USER = "postgres"
    private const val PASS = "wt_pass"
    private val PORT = getEnv("postgresPort")?.toIntOrNull() ?: 5432

    fun repoUnderTestContainer(
        initObjects: Collection<WtUb> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): IRepoUbInitialize = UbRepoInitialized(
        repo = RepoUbSql(
            SqlProperties(
                host = HOST,
                user = USER,
                password = PASS,
                port = PORT,
            ),
            randomUuid = randomUuid
        ),
        initObjects = initObjects,
    )
}

