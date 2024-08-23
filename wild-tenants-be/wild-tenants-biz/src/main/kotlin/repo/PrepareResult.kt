package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.models.WtWorkMode
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker


fun ICorChainDsl<WtContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != WtWorkMode.STUB }
    handle {
        response = ubRepoDone
        ubsResponse = ubsRepoDone
        state = when (val st = state) {
            WtState.RUNNING -> WtState.FINISHING
            else -> st
        }
    }
}
