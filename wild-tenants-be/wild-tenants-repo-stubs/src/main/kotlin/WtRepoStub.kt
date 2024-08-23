package ru.bugrimov.wt.backend.repository.inmemory

import ru.bugrimov.wt.common.models.UbPeriod
import ru.bugrimov.wt.common.repository.*
import ru.bugrimov.wt.stubs.WtUbStub

class WtRepoStub : IRepoUb {

    override suspend fun createUb(request: DbUbRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun readUb(request: DbUbIdRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun updateUb(request: DbUbRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun deleteUb(request: DbUbIdRequest) = DbUbResponseOk(data = WtUbStub.get())

    override suspend fun searchUb(filterRequest: DbUbFilterRequest) = DbUbsResponseOk(
        data = WtUbStub.prepareSearchList(period = UbPeriod.CURRENT)
    )
}