package ru.bugrimov.wt.common.helpers

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.logging.common.LogLevel

fun Throwable.asWtError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = WtError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun WtContext.addError(error: WtError) = errors.add(error)
fun WtContext.addErrors(error: Collection<WtError>) = errors.addAll(error)

fun WtContext.fail(error: WtError) {
    addError(error)
    state = WtState.FAILING
}

fun WtContext.fail(errors: Collection<WtError>) {
    addErrors(errors)
    state = WtState.FAILING
}

fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable,
) = WtError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    level = level,
    exception = e,
)

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = WtError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)