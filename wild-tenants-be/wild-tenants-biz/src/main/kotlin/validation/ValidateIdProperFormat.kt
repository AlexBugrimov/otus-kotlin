package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorValidation
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z#:-]+$")
    on { ubValidating.id != WtUbId.NONE && ! ubValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = ubValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only letters and numbers"
        )
        )
    }
}
