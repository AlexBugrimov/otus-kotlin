package ru.bugrimov.wt.common.ws

interface IWsSessionRepo {

    fun add(session: IWsSession): Boolean
    fun clearAll()
    fun remove(session: IWsSession): Boolean
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : IWsSessionRepo {
            override fun add(session: IWsSession): Boolean = false
            override fun clearAll() {}
            override fun remove(session: IWsSession): Boolean = false
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
