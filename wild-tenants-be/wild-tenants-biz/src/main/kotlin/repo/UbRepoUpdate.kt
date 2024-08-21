package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.repository.DbUbRequest
import ru.bugrimov.wt.common.repository.DbUbResponseErr
import ru.bugrimov.wt.common.repository.DbUbResponseErrWithData
import ru.bugrimov.wt.common.repository.DbUbResponseOk
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == WtState.RUNNING }
    handle {
        val request = DbUbRequest(ubRepoPrepare)
        when (val result = ubRepo.updateUb(request)) {
            is DbUbResponseOk -> ubRepoDone = result.data
            is DbUbResponseErr -> fail(result.errors)
            is DbUbResponseErrWithData -> {
                fail(result.errors)
                ubRepoDone = result.data
            }
        }
    }
}
