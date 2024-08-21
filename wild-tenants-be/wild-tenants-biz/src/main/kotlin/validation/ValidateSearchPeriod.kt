package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.errorValidation
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker
import java.time.LocalDate

fun ICorChainDsl<WtContext>.validateSearchPeriod(title: String) = worker {
    this.title = title
    this.description = """
        Валидация периода поиска в поисковых фильтрах. Допустимые значения:
        - текущий период
        - месяц/год в прошлом
    """.trimIndent()
    on {
        val date = ubFilterValidating.period.asDate()

        state == WtState.RUNNING &&
                date.isAfter(LocalDate.now())
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
