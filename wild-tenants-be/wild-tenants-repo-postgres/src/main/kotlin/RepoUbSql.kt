package ru.bugrimov.wt.backend.repo.postgresql

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.bugrimov.wt.backend.repo.postgresql.tables.UtilityBillTable
import ru.bugrimov.wt.common.helpers.asWtError
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.repository.*
import ru.otus.otuskotlin.marketplace.repo.common.IRepoUbInitialize

class RepoUbSql (
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IRepoUbInitialize {

    private val utilityBillTable = UtilityBillTable("${properties.schema}.${properties.utilityBillTable}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        utilityBillTable.deleteAll()
    }

    override fun save(ubs: Collection<WtUb>): Collection<WtUb> = ubs.map { saveObj(it) }

    override suspend fun createUb(request: DbUbRequest): IDbUbResponse = transactionWrapper {
        DbUbResponseOk(saveObj(request.ub))
    }

    override suspend fun readUb(request: DbUbIdRequest): IDbUbResponse = transactionWrapper {
        read(request.id)
    }

    override suspend fun updateUb(request: DbUbRequest): IDbUbResponse = update(request.ub.id, request.ub.lock) {
        utilityBillTable.update({ utilityBillTable.ubId eq request.ub.id.asString() }) {
            to(it, request.ub.copy(lock = WtUbLock(randomUuid())), randomUuid)
        }
        read(request.ub.id)
    }

    override suspend fun deleteUb(request: DbUbIdRequest): IDbUbResponse = update(request.id, request.lock) {
        utilityBillTable.deleteWhere { ubId eq request.id.asString() }
        DbUbResponseOk(it)
    }

    override suspend fun searchUb(filterRequest: DbUbFilterRequest): IDbUbsResponse = transactionWrapper({
        val res = utilityBillTable.selectAll().where {
            buildList {
                add(Op.TRUE)
                if (filterRequest.ownerId != WtUserId.NONE) {
                    add(utilityBillTable.owner eq filterRequest.ownerId.asString())
                }
                if (filterRequest.periodFilter != UbPeriod.CURRENT) {
                    add(
                        (utilityBillTable.month eq filterRequest.periodFilter.month)
                                and (utilityBillTable.year eq filterRequest.periodFilter.year)
                    )
                }
            }.reduce { a, b -> a and b }
        }
        DbUbsResponseOk(data = res.map { utilityBillTable.from(it) })
    }, {
        DbUbsResponseErr(it.asWtError())
    })

    private suspend inline fun <T> transactionWrapper(
        crossinline block: () -> T,
        crossinline handle: (Exception) -> T
    ): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbUbResponse): IDbUbResponse =
        transactionWrapper(block) { DbUbResponseErr(it.asWtError()) }

    private fun saveObj(ub: WtUb): WtUb = transaction(conn) {
        val res = utilityBillTable.insert {
            to(it, ub, randomUuid)
        }.resultedValues
            ?.map { utilityBillTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private fun read(id: WtUbId): IDbUbResponse {
        val ubId: String = id.asString()
        val res = utilityBillTable.selectAll().where {
            utilityBillTable.ubId eq ubId
        }.singleOrNull() ?: return errorNotFound(id)
        return DbUbResponseOk(utilityBillTable.from(res))
    }

    private suspend fun update(
        id: WtUbId,
        lock: WtUbLock,
        block: (WtUb) -> IDbUbResponse
    ): IDbUbResponse =
        transactionWrapper {
            if (id == WtUbId.NONE) return@transactionWrapper errorEmptyId

            val current = utilityBillTable.selectAll().where { utilityBillTable.ubId eq id.asString() }
                .singleOrNull()
                ?.let { utilityBillTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }
}