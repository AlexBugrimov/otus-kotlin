package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorValidation
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker
import java.math.BigDecimal

fun ICorChainDsl<WtContext>.validateMeterReadingsGreaterThanOrEqualToZero(title: String) = worker {
    this.title = title
    on {
        ubValidating.ubMeterReadings.isNotEmpty() && ubValidating.ubMeterReadings.any {
                    it.accruedSum < BigDecimal.ZERO ||
                    it.paidAmount < BigDecimal.ZERO ||
                    it.indicatedValue < BigDecimal.ZERO ||
                    it.volumeForPeriod < BigDecimal.ZERO
        }
    }
    handle {
        fail(
            errorValidation(
                field = "meter-readings",
                violationCode = "negative-value",
                description = "values must be positive or equal to zero"
            )
        )
    }
}