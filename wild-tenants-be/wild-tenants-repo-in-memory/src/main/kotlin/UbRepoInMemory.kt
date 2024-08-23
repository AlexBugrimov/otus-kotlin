package ru.bugrimov.wt.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.models.WtUbLock
import ru.bugrimov.wt.common.models.WtUserId
import ru.bugrimov.wt.common.repository.*
import ru.otus.otuskotlin.marketplace.repo.common.IRepoUbInitialize
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import ru.bugrimov.wt.common.repository.exceptions.RepoEmptyLockException

class UbRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : UbRepoBase(), IRepoUb, IRepoUbInitialize {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, UbEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(ubs: Collection<WtUb>) = ubs.map { ub ->
        val entity = UbEntity(ub)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ub
    }

    override suspend fun createUb(request: DbUbRequest): IDbUbResponse = tryUbMethod {
        val key = randomUuid()
        val ub = request.ub.copy(id = WtUbId(key), lock = WtUbLock(randomUuid()))
        val entity = UbEntity(ub)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbUbResponseOk(ub)
    }

    override suspend fun readUb(request: DbUbIdRequest): IDbUbResponse = tryUbMethod {
        val key = request.id.takeIf { it != WtUbId.NONE }?.asString() ?: return@tryUbMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbUbResponseOk(it.toInternal())
                } ?: errorNotFound(request.id)
        }
    }

    override suspend fun updateUb(request: DbUbRequest): IDbUbResponse = tryUbMethod {
        val rqAd = request.ub
        val id = rqAd.id.takeIf { it != WtUbId.NONE } ?: return@tryUbMethod errorEmptyId
        val key = id.asString()
        val oldLock = rqAd.lock.takeIf { it != WtUbLock.NONE } ?: return@tryUbMethod errorEmptyLock(id)

        mutex.withLock {
            val oldAd = cache.get(key)?.toInternal()
            when {
                oldAd == null -> errorNotFound(id)
                oldAd.lock == WtUbLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldAd.lock != oldLock -> errorRepoConcurrency(oldAd, oldLock)
                else -> {
                    val newAd = rqAd.copy(lock = WtUbLock(randomUuid()))
                    val entity = UbEntity(newAd)
                    cache.put(key, entity)
                    DbUbResponseOk(newAd)
                }
            }
        }
    }

    override suspend fun deleteUb(request: DbUbIdRequest): IDbUbResponse = tryUbMethod {
        val id = request.id.takeIf { it != WtUbId.NONE } ?: return@tryUbMethod errorEmptyId
        val key = id.asString()
        val oldLock = request.lock.takeIf { it != WtUbLock.NONE } ?: return@tryUbMethod errorEmptyLock(id)

        mutex.withLock {
            val oldAd = cache.get(key)?.toInternal()
            when {
                oldAd == null -> errorNotFound(id)
                oldAd.lock == WtUbLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldAd.lock != oldLock -> errorRepoConcurrency(oldAd, oldLock)
                else -> {
                    cache.invalidate(key)
                    DbUbResponseOk(oldAd)
                }
            }
        }
    }

    override suspend fun searchUb(filterRequest: DbUbFilterRequest): IDbUbsResponse = tryUbsMethod {
        val result: List<WtUb> = cache.asMap().asSequence()
            .filter { entry ->
                filterRequest.ownerId.takeIf { it != WtUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                filterRequest.periodFilter.let {
                    (entry.value.yearPeriod == it.year) && (entry.value.monthPeriod == it.month)
                }
            }
            .map { it.value.toInternal() }
            .toList()
        DbUbsResponseOk(result)
    }
}
