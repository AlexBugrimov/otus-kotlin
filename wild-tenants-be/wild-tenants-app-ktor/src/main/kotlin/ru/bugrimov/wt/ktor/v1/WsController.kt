package ru.bugrimov.wt.ktor.v1

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.bugrimov.wild_tenants.be.api.v1.models.IRequest
import ru.bugrimov.wt.api.v1.apiV1Mapper
import ru.bugrimov.wt.app.common.AppSettings
import ru.bugrimov.wt.app.common.controllerHelper
import ru.bugrimov.wt.common.models.WtCommand
import ru.bugrimov.wt.ktor.base.KtorWsSessionV1
import ru.bugrimov.wt.mappers.fromTransport
import ru.bugrimov.wt.mappers.toInit
import ru.bugrimov.wt.mappers.toTransportUb
import kotlin.reflect.KClass

private val clWsV1: KClass<*> = WebSocketSession::wsHandlerV1::class

suspend fun WebSocketSession.wsHandlerV1(appSettings: AppSettings) = with(KtorWsSessionV1(this)) {
    val sessions = appSettings.corSettings.wsSessions
    sessions.add(this)

    appSettings.controllerHelper(
        {
            command = WtCommand.INIT
            wsSession = this@with
        },
        { outgoing.send(Frame.Text(apiV1Mapper.writeValueAsString(toInit()))) },
        clWsV1,
        "wsV1-init"
    )

    incoming.receiveAsFlow().mapNotNull {
        val frame = it as? Frame.Text ?: return@mapNotNull
        try {
            appSettings.controllerHelper(
                {
                    fromTransport(apiV1Mapper.readValue<IRequest>(frame.readText()))
                    wsSession = this@with
                },
                {
                    val result = apiV1Mapper.writeValueAsString(toTransportUb())
                    outgoing.send(Frame.Text(result))
                },
                clWsV1,
                "wsV1-handle"
            )

        } catch (_: ClosedReceiveChannelException) {
            sessions.remove(this@with)
        } finally {
            appSettings.controllerHelper(
                {
                    command = WtCommand.FINISH
                    wsSession = this@with
                },
                { },
                clWsV1,
                "wsV1-finish"
            )
            sessions.remove(this@with)
        }
    }.collect()
}
