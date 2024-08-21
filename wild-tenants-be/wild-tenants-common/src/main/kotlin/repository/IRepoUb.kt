package ru.bugrimov.wt.common.repository

interface IRepoUb {
    suspend fun createUb(request: DbUbRequest): IDbUbResponse
    suspend fun readUb(request: DbUbIdRequest): IDbUbResponse
    suspend fun updateUb(request: DbUbRequest): IDbUbResponse
    suspend fun deleteUb(request: DbUbIdRequest): IDbUbResponse
    suspend fun searchUb(filterRequest: DbUbFilterRequest): IDbUbsResponse

    companion object {
        val NONE = object : IRepoUb {
            override suspend fun createUb(request: DbUbRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readUb(request: DbUbIdRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateUb(request: DbUbRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteUb(request: DbUbIdRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchUb(filterRequest: DbUbFilterRequest): IDbUbsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}