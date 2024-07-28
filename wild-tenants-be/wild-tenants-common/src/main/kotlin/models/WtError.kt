package ru.bugrimov.windtenants.common.models

import ru.bugrimov.wild_tenants.logging.common.LogLevel

data class WtError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
