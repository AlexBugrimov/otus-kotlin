package ru.bugrimov.wt.biz.stubs

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.common.models.WtState.FINISHING
import ru.bugrimov.wt.common.models.WtState.RUNNING
import ru.bugrimov.wt.common.stubs.WtStubs.SUCCESS
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker
import ru.bugrimov.wt.logging.common.LogLevel
import ru.bugrimov.wt.stubs.WtUbStub

fun ICorChainDsl<WtContext>.stubSearchSuccess(title: String, corSettings: WtCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска квитанции
    """.trimIndent()
    on { stubCase == SUCCESS && state == RUNNING }
    val logger = corSettings.loggerProvider.logger("stubUbSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = FINISHING
            ubsResponse.addAll(WtUbStub.prepareSearchList(filterRequest.period))
        }
    }
}
