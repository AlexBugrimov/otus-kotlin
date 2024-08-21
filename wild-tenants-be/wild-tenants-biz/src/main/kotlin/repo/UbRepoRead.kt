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


fun ICorChainDsl<WtContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение квитанции из БД"
    on { state == WtState.RUNNING }
    handle {
        val request = DbUbIdRequest(ubValidated)
        when (val result = ubRepo.readUb(request)) {
            is DbUbResponseOk -> ubRepoRead = result.data
            is DbUbResponseErr -> fail(result.errors)
            is DbUbResponseErrWithData -> {
                fail(result.errors)
                ubRepoRead = result.data
            }
        }
    }
}
