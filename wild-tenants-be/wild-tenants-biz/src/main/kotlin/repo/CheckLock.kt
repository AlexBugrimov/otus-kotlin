package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.common.repository.errorRepoConcurrency
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker


fun ICorChainDsl<WtContext>.checkLock(title: String) = worker {
    this.title = title
    description = """
        Проверка оптимистичной блокировки. Если не равна сохраненной в БД, значит данные запроса устарели 
        и необходимо их обновить вручную
    """.trimIndent()
    on { state == WtState.RUNNING && ubValidated.lock != ubRepoRead.lock }
    handle {
        fail(errorRepoConcurrency(ubRepoRead, ubValidated.lock).errors)
    }
}
