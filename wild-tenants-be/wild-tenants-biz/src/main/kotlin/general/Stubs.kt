package ru.bugrimov.wt.biz.general

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.models.WtWorkMode
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.chain

fun ICorChainDsl<WtContext>.stubs(title: String, block: ICorChainDsl<WtContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == WtWorkMode.STUB && state == WtState.RUNNING }
}
