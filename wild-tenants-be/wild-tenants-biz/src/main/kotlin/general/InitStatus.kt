package ru.bugrimov.wt.biz.general

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker


fun ICorChainDsl<WtContext>.initStatus(title: String) = worker() {
    this.title = title
    this.description = """
        Этот обработчик устанавливает стартовый статус обработки. Запускается только в случае не заданного статуса.
    """.trimIndent()
    on { state == WtState.NONE }
    handle { state = WtState.RUNNING }
}
