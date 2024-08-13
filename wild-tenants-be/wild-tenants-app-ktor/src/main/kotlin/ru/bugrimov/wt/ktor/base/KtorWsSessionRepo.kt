package ru.bugrimov.wt.ktor.base

import io.ktor.util.collections.*
import ru.bugrimov.wt.common.ws.IWsSession
import ru.bugrimov.wt.common.ws.IWsSessionRepo

class KtorWsSessionRepo(private val sessions: MutableSet<IWsSession> = ConcurrentSet()) : IWsSessionRepo {

    override fun add(session: IWsSession) = sessions.add(session)

    override fun clearAll() = sessions.clear()

    override fun remove(session: IWsSession) = sessions.remove(session)

    override suspend fun <T> sendAll(obj: T) = sessions.forEach { it.send(obj) }
}
