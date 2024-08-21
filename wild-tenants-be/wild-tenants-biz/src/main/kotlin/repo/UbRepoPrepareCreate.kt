package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker
import ru.bugrimov.wt.stubs.WtUbStub


fun ICorChainDsl<WtContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == WtState.RUNNING }
    handle {
        ubRepoPrepare = ubValidated.deepCopy()
        ubRepoPrepare.ownerId = WtUbStub.get().ownerId
    }
}
