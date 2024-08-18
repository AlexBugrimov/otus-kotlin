package ru.bugrimov.wt.biz.stubs

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.stubs.WtStubs
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker
import ru.bugrimov.wt.logging.common.LogLevel
import ru.bugrimov.wt.stubs.WtUbStub

fun ICorChainDsl<WtContext>.stubUpdateSuccess(title: String, corSettings: WtCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для изменения квитанции
    """.trimIndent()
    on { stubCase == WtStubs.SUCCESS && state == WtState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubUbSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = WtState.FINISHING
            val stub = WtUbStub.prepareResult {
                request.id.takeIf { it != WtUbId.NONE }?.also { this.id = it }
                request.ubMeterReadings.also { this.ubMeterReadings = it }
                request.ubPeriod.also { this.ubPeriod = it }
                request.ownerId.also { this.ownerId = it }
            }
            response = stub
        }
    }
}
