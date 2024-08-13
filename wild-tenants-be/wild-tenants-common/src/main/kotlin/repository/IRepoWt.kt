package ru.bugrimov.wt.common.repository

interface IRepoWt {
    suspend fun createUb(request: DbRequest): IDbUbResponse
    suspend fun readUb(request: DbIdRequest): IDbUbResponse
    suspend fun updateUb(request: DbRequest): IDbUbResponse
    suspend fun deleteUb(request: DbIdRequest): IDbUbResponse
    suspend fun searchUb(filterRequest: DbFilterRequest): IDbUbsResponse

    companion object {
        val NONE = object : IRepoWt {
            override suspend fun createUb(request: DbRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readUb(request: DbIdRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateUb(request: DbRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteUb(request: DbIdRequest): IDbUbResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchUb(filterRequest: DbFilterRequest): IDbUbsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}