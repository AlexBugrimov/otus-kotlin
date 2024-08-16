package ru.bugrimov.wt.backend.repository.inmemory

import ru.bugrimov.wt.common.repository.*
import ru.bugrimov.wt.stubs.WtUbStub

class WtRepoStub : IRepoWt {

    override suspend fun createUb(request: DbRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun readUb(request: DbIdRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun updateUb(request: DbRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun deleteUb(request: DbIdRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun searchUb(filterRequest: DbFilterRequest) = DbUbsResponseOk(
        data = WtUbStub.prepareSearchList(filter = "")
    )
}