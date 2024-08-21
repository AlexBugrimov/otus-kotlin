package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.finishValidation(title: String) = worker {
    this.title = title
    on { state == WtState.RUNNING }
    handle {
        ubValidated = ubValidating
    }
}

fun ICorChainDsl<WtContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == WtState.RUNNING }
    handle {
        ubFilterValidated = ubFilterValidating
    }
}
