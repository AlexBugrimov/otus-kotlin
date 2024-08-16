package ru.bugrimov.wt.ktor.plugins

import io.ktor.server.application.*
import ru.bugrimov.wt.logging.common.LoggerProvider
import ru.bugrimov.wt.logging.socket.SocketLoggerSettings
import ru.bugrimov.wt.logging.socket.loggerSocket

fun Application.getSocketLoggerProvider(): LoggerProvider {
    val loggerSettings = environment.config.config("ktor.socketLogger").let { conf ->
        SocketLoggerSettings(
            host = conf.propertyOrNull("host")?.getString() ?: "127.0.0.1",
            port = conf.propertyOrNull("port")?.getString()?.toIntOrNull() ?: 9002,
        )
    }
    return LoggerProvider { loggerSocket(it, loggerSettings) }
}
