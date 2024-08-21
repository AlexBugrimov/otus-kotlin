package ru.bugrimov.wt.biz.stubs

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Запрошен кейс, который не поддерживается в заглушкой
    """.trimIndent()
    on { state == WtState.RUNNING }
    handle {
        fail(
            WtError(
                code = "validation",
                field = "stubs",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
