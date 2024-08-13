package ru.bugrimov.wt.common.models

import ru.bugrimov.wt.logging.common.LogLevel

data class WtError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
