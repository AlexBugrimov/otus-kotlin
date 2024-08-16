package ru.bugrimov.wt.ktor.base

import io.ktor.websocket.*
import ru.bugrimov.wild_tenants.be.api.v1.models.IResponse
import ru.bugrimov.wt.api.v1.apiV1ResponseSerialize
import ru.bugrimov.wt.common.ws.IWsSession

data class KtorWsSessionV1(
    private val session: WebSocketSession
) : IWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is IResponse)
        session.send(Frame.Text(apiV1ResponseSerialize(obj)))
    }
}
