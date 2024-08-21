package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.repository.DbUbFilterRequest
import ru.bugrimov.wt.common.repository.DbUbsResponseErr
import ru.bugrimov.wt.common.repository.DbUbsResponseOk
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск квитанции в БД по фильтру"
    on { state == WtState.RUNNING }
    handle {
        val request = DbUbFilterRequest(
            periodFilter = ubFilterValidated.period,
            ownerId = ubFilterValidated.ownerId
        )
        when (val result = ubRepo.searchUb(request)) {
            is DbUbsResponseOk -> ubsRepoDone = result.data.toMutableList()
            is DbUbsResponseErr -> fail(result.errors)
        }
    }
}
