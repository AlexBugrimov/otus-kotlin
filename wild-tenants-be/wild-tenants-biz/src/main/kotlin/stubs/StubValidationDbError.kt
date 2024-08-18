package ru.bugrimov.wt.biz.stubs

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.stubs.WtStubs
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == WtStubs.DB_ERROR && state == WtState.RUNNING }
    handle {
        fail(
            WtError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
