package ru.bugrimov.wt.biz.general

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtCommand
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.chain


fun ICorChainDsl<WtContext>.operation(
    title: String,
    command: WtCommand,
    block: ICorChainDsl<WtContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == WtState.RUNNING }
}
