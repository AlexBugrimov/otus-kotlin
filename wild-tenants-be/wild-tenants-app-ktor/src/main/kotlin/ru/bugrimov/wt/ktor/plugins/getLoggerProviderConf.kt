package ru.bugrimov.wt.ktor.plugins

import io.ktor.server.application.*
import ru.bugrimov.wt.logging.common.LoggerProvider
import ru.bugrimov.wt.logging.loggerLogback

fun Application.getLoggerProviderConf(): LoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> LoggerProvider { loggerLogback(it) }
        "socket", "sock" -> getSocketLoggerProvider()
        else -> throw Exception("Logger $mode is not allowed.")
    }
