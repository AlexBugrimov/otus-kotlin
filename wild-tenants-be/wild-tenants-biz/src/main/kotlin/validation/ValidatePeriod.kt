package ru.bugrimov.wt.biz.validation

import kotlinx.datetime.number
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorValidation
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker
import java.time.LocalDate

fun ICorChainDsl<WtContext>.validatePeriod(title: String) = worker {
    this.title = title
    on {
        ubValidating.ubPeriod.year > LocalDate.now().year ||
                ubValidating.ubPeriod.month.number > LocalDate.now().month.number
    }
    handle {
        fail(
            errorValidation(
                field = "period",
                violationCode = "invalid-period",
                description = "period must be current or in the past"
            )
        )
    }
}