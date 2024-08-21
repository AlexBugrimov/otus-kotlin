package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorValidation
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtUbLock
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { ubValidating.lock != WtUbLock.NONE && !ubValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = ubValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
