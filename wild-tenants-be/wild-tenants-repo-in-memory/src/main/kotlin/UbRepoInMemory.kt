package ru.bugrimov.wt.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import ru.bugrimov.wt.common.repository.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class UbRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : UbRepoBase() {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, UbEntity>()
        .expireAfterWrite(ttl)
        .build()

    override suspend fun createUb(request: DbRequest): IDbUbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun readUb(request: DbIdRequest): IDbUbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateUb(request: DbRequest): IDbUbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUb(request: DbIdRequest): IDbUbResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchUb(filterRequest: DbFilterRequest): IDbUbsResponse {
        TODO("Not yet implemented")
    }


}
