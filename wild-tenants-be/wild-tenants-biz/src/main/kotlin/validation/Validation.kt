package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.chain

fun ICorChainDsl<WtContext>.validation(block: ICorChainDsl<WtContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == WtState.RUNNING }
}
