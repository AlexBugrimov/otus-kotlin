package ru.bugrimov.wt.logging.socket

import kotlinx.serialization.Serializable
import ru.bugrimov.wt.logging.common.LogLevel

@Serializable
data class LogData(
    val level: LogLevel,
    val message: String,
)
