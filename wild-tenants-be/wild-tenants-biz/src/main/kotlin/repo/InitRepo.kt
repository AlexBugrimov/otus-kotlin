package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.biz.exceptions.WtUbDbNotConfiguredException
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorSystem
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtWorkMode
import ru.bugrimov.wt.common.repository.IRepoUb
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        ubRepo = when (workMode) {
            WtWorkMode.TEST -> corSettings.repoTest
            WtWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != WtWorkMode.STUB && ubRepo == IRepoUb.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = WtUbDbNotConfiguredException(workMode)
            )
        )
    }
}
