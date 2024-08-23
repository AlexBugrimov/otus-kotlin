package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.repository.DbUbIdRequest
import ru.bugrimov.wt.common.repository.DbUbResponseErr
import ru.bugrimov.wt.common.repository.DbUbResponseErrWithData
import ru.bugrimov.wt.common.repository.DbUbResponseOk
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление квитанции из БД по ID"
    on { state == WtState.RUNNING }
    handle {
        val request = DbUbIdRequest(ubRepoPrepare)
        when (val result = ubRepo.deleteUb(request)) {
            is DbUbResponseOk -> ubRepoDone = result.data
            is DbUbResponseErr -> {
                fail(result.errors)
                ubRepoDone = ubRepoRead
            }

            is DbUbResponseErrWithData -> {
                fail(result.errors)
                ubRepoDone = result.data
            }
        }
    }
}
