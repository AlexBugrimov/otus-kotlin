package ru.bugrimov.wt.biz.stubs

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.helpers.fail
import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtState.RUNNING
import ru.bugrimov.wt.common.stubs.WtStubs.BAD_METER_READING
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker

fun ICorChainDsl<WtContext>.stubValidationBadMeterReadings(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для показаний счетчиков
    """.trimIndent()
    on { stubCase == BAD_METER_READING && state == RUNNING }
    handle {
        fail(
            WtError(
                group = "validation",
                code = "validation-meter-readings",
                field = "meter-readings",
                message = "Wrong meter-readings field"
            )
        )
    }
}
