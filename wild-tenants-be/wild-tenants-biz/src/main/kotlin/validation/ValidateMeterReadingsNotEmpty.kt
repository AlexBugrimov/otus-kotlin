package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorValidation
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.validateMeterReadingsNotEmpty(title: String) = worker {
    this.title = title
    on { ubValidating.ubMeterReadings.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "meter-readings",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
