package ru.bugrimov.wt.ktor

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.bugrimov.wt.api.v1.apiV1Mapper
import ru.bugrimov.wt.app.common.AppSettings
import ru.bugrimov.wt.ktor.plugins.settings
import ru.bugrimov.wt.ktor.v1.v1Ub
import ru.bugrimov.wt.ktor.v1.wsHandlerV1

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module(
    settings: WtAppSettings = settings()
) {

    install(WebSockets)

    routing {
        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }
            v1Ub(settings)
            webSocket("/ws") {
                wsHandlerV1(settings)
            }
        }
    }
}