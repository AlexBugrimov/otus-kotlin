package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.repository.*

class UbRepositoryMock(
    private val invokeCreateUb: (DbUbRequest) -> IDbUbResponse = { DEFAULT_UB_SUCCESS_EMPTY_MOCK },
    private val invokeReadUb: (DbUbIdRequest) -> IDbUbResponse = { DEFAULT_UB_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateUb: (DbUbRequest) -> IDbUbResponse = { DEFAULT_UB_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteUb: (DbUbIdRequest) -> IDbUbResponse = { DEFAULT_UB_SUCCESS_EMPTY_MOCK },
    private val invokeSearchUb: (DbUbFilterRequest) -> IDbUbsResponse = { DEFAULT_UBS_SUCCESS_EMPTY_MOCK },
): IRepoUb {
    override suspend fun createUb(request: DbUbRequest): IDbUbResponse {
        return invokeCreateUb(request)
    }

    override suspend fun readUb(request: DbUbIdRequest): IDbUbResponse {
        return invokeReadUb(request)
    }

    override suspend fun updateUb(request: DbUbRequest): IDbUbResponse {
        return invokeUpdateUb(request)
    }

    override suspend fun deleteUb(request: DbUbIdRequest): IDbUbResponse {
        return invokeDeleteUb(request)
    }

    override suspend fun searchUb(filterRequest: DbUbFilterRequest): IDbUbsResponse {
        return invokeSearchUb(filterRequest)
    }

    companion object {
        val DEFAULT_UB_SUCCESS_EMPTY_MOCK = DbUbResponseOk(WtUb())
        val DEFAULT_UBS_SUCCESS_EMPTY_MOCK = DbUbsResponseOk(emptyList())
    }
}
